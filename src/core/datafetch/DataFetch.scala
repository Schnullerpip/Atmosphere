package core.datafetch

import java.io.File

/**
  * Created by julian on 05-Mar-17.
  */
object DataFetch {

  /**
    * returns a tuple containing two lists -> 1. List of all files in this root, 2. list of all directories in this root
    * @param root the (hopefully) directory, that should be traversed
    * @return one list with with all the files within root and one list with all directories within root
    * */
  def filesAndFolders(root:File):(List[File], List[File]) = {
    if(root.exists && root.isDirectory)
        root.listFiles.toList partition (_.isFile)
    else
      (List(), List())
  }
}
