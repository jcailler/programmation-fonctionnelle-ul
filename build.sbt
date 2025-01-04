import scala.collection.mutable
import scala.util.matching.Regex

scalaVersion := "3.5.0"

/** Get all sub-projects in the `exercises`, `labs`, and `exams` directories. */
def sourceSubProjects(): Seq[Project] = {
  List("TP", "Projects")
    .map(file)
    .flatMap(findProjects)
    .map(dir => Project(toCamelCase(dir.name), dir))
}

/** Get all generated sub-projects in the `generated/code` directory.
 *
 *  If `isSolution` is `true`, only include projects whose directory name
 *  contains `-solution`. If `isSolution` is `false`, only include projects
 *  whose directory name does not contain `-solution`.
 */
def generatedSubProjects(isSolution: Boolean): Seq[Project] = {
  val projectDirPattern: Regex = """^(.*?)(-solution)?(-[0-9A-Fa-f]{12})?""".r
  findProjects(file("generated") / "code")
    .flatMap(dir => {
      val projectDirPattern(projectName, solutionSuffix, _) = dir.name
      // Note: the first parameter to `Project` is the project ID. It is not
      // necessarily the same as the project name, which is set in child
      // projects' `build.sbt` files.
      val project = Project(toCamelCase(projectName), dir)
      if (isSolution == (solutionSuffix != null)) Some(project) else None
    })
}

lazy val make = project.in(file("make"))

val subProjects = sys.env.get("INCLUDE_GENERATED_PROJECTS") match {
  case Some("solutions") => generatedSubProjects(true)
  case Some("scaffolds") => generatedSubProjects(false)
  case _ => sourceSubProjects()
}

lazy val root = new CompositeProject {
  override def componentProjects: Seq[Project] = subProjects
}

/** Lists all directories in the given directory. */
def findProjects(file: File): Seq[File] = {
  val subFiles = file.listFiles()
  if (subFiles == null) Seq.empty
  else if (subFiles.exists(_.getName == "build.sbt")) Seq(file)
  else subFiles.sortBy(_.getName).flatMap(findProjects)
}

/** Converts a string from kebab-case to camelCase. */
def toCamelCase(s: String): String = {
  val parts = s.split("-")
  parts.head + parts.tail.map(part => part.head.toUpper + part.tail).mkString
}

enablePlugins(JmhPlugin)


lazy val verifyConsistency = TaskKey[Unit](
  "verifyConsistency", "Verifies the consistency of pre-defined settings across projects"
)
verifyConsistency := Def.taskDyn {
  final case class SettingMismatchException(msg: String) extends RuntimeException(msg)

  final case class CompositeException(causes: Iterable[Throwable]) extends RuntimeException(
    "Underlying exceptions: \n\t - " +
      causes.map(cause => cause.getMessage)
        .mkString("\n\t - ")
  )

  final case class SettingRecord(projectId: String, settingLabel: String, settingValue: Any)
  final case class LibraryRecord(projectId: String, libId: String, version: String, scope: Option[String])

  type ErrorMsg = String
  type ProjectId = String


  def checkOccurrencesConsistency[Any](
    criteriaName: String,
    occurrences: Seq[(ProjectId, Any)],
    task: Boolean = false,
    allowedOutliers: Seq[Any] = Seq()
  ): Def.Initialize[Task[Seq[ErrorMsg]]] = {
    flattenTasks(
      if (task) {
        occurrences.map(task =>
          Def.task(None) // This block is needed as the scalaCOptions is a task that must be resolved to get its value
            .flatMap(ignored =>
              task._2.asInstanceOf[Task[Any]].map(settingValue =>
                (task._1, settingValue)
              )
            )
        )
      } else {
        occurrences.map(occurrence => Def.task(occurrence))
      }
    ).map(occurrenceList => {
      val criteriaOccurrences = occurrenceList
        .filterNot(record => allowedOutliers.contains(record._2))
        .groupBy(record => record._2)
      if (criteriaOccurrences.size > 1) {
        criteriaOccurrences.toSeq.map(occurrence =>
          criteriaName + " on " +
            occurrence._1 + ", uses: " + occurrence._2.size + " in " +
            occurrence._2.map(record => record._1)
        )
      } else {
        Seq()
      }
    })
  }

  def checkLibraries(libraryVersions: Seq[(ProjectId, ?)]): Def.Initialize[Task[Seq[ErrorMsg]]] = {
    val libraryVersionRecords = mutable.Map[String, String]()
    val libraryScopeRecords = mutable.Map[String, Option[String]]()

    flattenTasks(
      libraryVersions.flatMap(libraryVersion => {
          val projectId = libraryVersion._1
          libraryVersion._2 match {
            case libraries: List[Any] => libraries map { library => convertToLibRecord(library.toString, projectId) }
            case _ => throw new IllegalStateException("Unexpected library format...")
          }
        })
        .groupBy(lib => lib.libId)
        .map(libVersion => {
          val (libId, libInstances) = libVersion
          flattenTasks(
            Seq(
              checkLibraryVersions(libId, libInstances),
              // checkLibraryScopes(libInstances)
            )
          ).map(listOfList => listOfList.flatten)
        }).toSeq
    ).map(listOfList => listOfList.flatten)
  }

  def convertToLibRecord(library: String, projectId: ProjectId): LibraryRecord = {
    val libraryParts = library.split(":")
    val libraryId = libraryParts(0) + ":" + libraryParts(1)
    val version = libraryParts(2)
    val scope = if (libraryParts.length == 4) {
      Some(libraryParts(3))
    } else {
      None
    }

    LibraryRecord(projectId, libraryId, version, scope)
  }

  def checkLibraryVersions(libId: String, libInstances: Seq[LibraryRecord]) = {
    checkOccurrencesConsistency(libId + "'s version", libInstances.map(lib => (lib.projectId, lib.version)))
  }

  def checkLibraryScopes(libId: String, libInstances: Seq[LibraryRecord]) = {
    checkOccurrencesConsistency(libId + "'s version", libInstances.map(lib => (lib.projectId, lib.scope)))
  }

  val verifiedSettings = Map[String, Seq[(ProjectId, ?)] => Def.Initialize[Task[Seq[ErrorMsg]]]](
    "scalaVersion" -> (
      versions => checkOccurrencesConsistency("scalaVersion", versions.map(version => (version._1, version._2)))
      ),
    "libraryDependencies" -> checkLibraries,
    "scalacOptions" -> (
      options => checkOccurrencesConsistency(
        "scalaCOptions",
        options.map(option => (option._1, option._2)),
        task = true,
        allowedOutliers = Seq(List(
          "-scalajs" // This compiler options is added automatically in webapp/lib/client
        ))
      )
      )
  )

  Def.taskDyn {
    val settingsList = Def.taskDyn {
      val build = buildStructure.value
      flattenTasks(
        build.allProjects.flatMap(project =>
          project.settings
            .filter(setting => setting.key.scope == Scope(This, This, This, This))
            .filter(setting => verifiedSettings.keys.toList contains setting.key.key.label)
            // Throws "referencing not found setting" if removed
            .filter(setting => !project.base.getPath.endsWith("js"))
            .map(setting =>
              Def.taskDyn {
                setting.init.map { settingValue =>
                  val projectParentName = project.base
                    .getParentFile
                    .relativeTo(baseDirectory.value)
                    .map[String](file => file.toString)
                    .getOrElse("")
                    .replace(java.io.File.separator, "/")
                  val projectName = projectParentName + "/" + project.id
                  SettingRecord(projectName, setting.key.key.label, settingValue)
                }
              }
            )
        )
      )
    }.value
    // Group the setting depending on its type (scalaVersion, libraryDependencies, etc...)
    flattenTasks(
      settingsList.groupBy(settingRecord => settingRecord.settingLabel)
        .map { settingsGroup =>
          val (settingName, settingInstances) = settingsGroup

          val settingValues = settingInstances.map(settingRecord =>
            (settingRecord.projectId, settingRecord.settingValue)
          )

          val verificationTask = verifiedSettings(settingName)(settingValues)
          verificationTask.map(errors =>
            errors.map(errorMsg =>
              SettingMismatchException(errorMsg)
            )
          )
        }.toSeq
    ).map(errors => {
      val flattenedErrors = errors.flatten
      if (flattenedErrors.nonEmpty) {
        throw CompositeException(flattenedErrors)
      }
      println(
        "[info] Project's build settings consistency verified across the following:" +
          "\n\t - " + verifiedSettings.keys.mkString("\n\t - ")
      )
      None
    })
  }
}.value


def flattenTasks[A](tasks: scala.collection.Seq[Def.Initialize[Task[A]]]): Def.Initialize[Task[List[A]]] = {
  tasks.toList match {
    case Nil => Def.task {
      Nil
    }
    case x :: xs => Def.taskDyn {
      flattenTasks(xs) map (x.value :: _)
    }
  }
}

lazy val startupTransition: State => State = { s: State =>
  verifyConsistency.key.label :: s
}

(Global / onLoad) := {
  // https://www.scala-sbt.org/1.x/docs/Howto-Startup.html
  val old = (Global / onLoad).value
  startupTransition compose old
}
