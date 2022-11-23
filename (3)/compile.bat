javac .\JavaFX\Server.java

javac .\JavaFX\model\Model.java

javac --module-path .\Libs\javafx-sdk-19\lib --add-modules=javafx.controls,javafx.fxml --class-path ".\JavaFX" .\JavaFX\controller\Controller.java
javac --module-path .\Libs\javafx-sdk-19\lib --add-modules=javafx.controls,javafx.fxml --class-path ".\JavaFX" .\JavaFX\Main.java

pause