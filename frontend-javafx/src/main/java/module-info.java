module com.example.frontendjavafx {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.slf4j;

	requires unirest.java;
	requires unirest.objectmapper.jackson;
//	requires java.sql;

	requires static lombok;

	opens com.github.thofis.contacts.frontendjavafx to javafx.fxml;
	exports com.github.thofis.contacts.frontendjavafx;
}
