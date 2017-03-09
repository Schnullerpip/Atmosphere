package core

/**
  * Created by julian on 09-Mar-17.
  */
object Prototype {
  val placeholder = "PLACEHOLDER"
  val hrefPlaceholder = "HREFPLACEHOLDER"
  val btnPlaceholder = "BTNPLACEHOLDER"
  val playPlaceholder = "PLAYPLACEHOLDER"
  val pausePlaceholder = "PAUSEPLACEHOLDER"
  val repeatPlaceholder = "REPEATPLACEHOLDER"
  val scriptPlaceholder = "SCRIPTPLACEHOLDER"
  val idPlaceHolder = "IDPLACEHOLDER"
  val progressPlaceholder = "PROGRESSPLACEHOLDER"
  val progressBar =
    s"""
      <div class="progress">
        <div class="progress-bar progress-bar-striped active" role="progressbar" id="$idPlaceHolder"
          aria-valuenow="0"  aria-valuemin="0"
          aria-valuemax="100" style="width:0%">
        </div>
      </div>
    """

  val buttonFolder: String =  "<a class=\"btn btn-default btn-block " + btnPlaceholder + "\" href=\"" + hrefPlaceholder + "\">"+placeholder+"</a>"
  val buttonFile: String =    "<a class=\"btn btn-default btn-block " + btnPlaceholder +
    "\" href=\"" + hrefPlaceholder + "\">"+placeholder + progressPlaceholder + "</a>"
  val options: String =
    s"""
      <div class="btn-group">
       <a class="btn btn-primary" href="$playPlaceholder">
          <span class="glyphicon glyphicon-play"></span>
       </a>
       <a class="btn btn-primary" href="$pausePlaceholder">
          <span class="glyphicon glyphicon-pause"></span>
       </a>
       <a class="btn btn-primary" href="$repeatPlaceholder">
          <span class="glyphicon glyphicon-repeat"></span>
       </a>
      </div>
    """.stripMargin
}
