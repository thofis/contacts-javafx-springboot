module com.example.frontendjavafx {
	requires javafx.controls;
	requires javafx.fxml;


	opens com.example.frontendjavafx to javafx.fxml;
	exports com.example.frontendjavafx;
}
