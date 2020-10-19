package imc.dad;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;


public class Interfaz extends Application {
	private Label peso,altura,kg,cm,imcLabel,formulaImc;
	private TextField heightFieldText,wheightFieldText;
	private HBox wheightHbox,heightHbox,formuleHbox;
	private VBox imcVbox;
	private Scene imcScene;
	private Label clasificacion = new Label();
	DoubleProperty pesoParaImc;
	DoubleProperty alturaParaImc;
	DoubleProperty calculoImc;
	StringExpression aux;
	
	public void start(Stage primaryStage) throws Exception {
		startWheight();
		startHeight();
		wheightHbox();
		heightHbox();
		startFormuleLabels();
		formuleHBox();
		startImc();
		bindingsWheightHeight();
		
		imcScene=new Scene(imcVbox,300,300);
		primaryStage.setScene(imcScene);
		primaryStage.setTitle("IMC");
		primaryStage.show();
				
		}
	@SuppressWarnings("unchecked")
	private void bindingsWheightHeight() {
		
		pesoParaImc = new SimpleDoubleProperty();
		alturaParaImc = new SimpleDoubleProperty();
		calculoImc=new SimpleDoubleProperty();
		calculoImc.bind(pesoParaImc.divide((alturaParaImc.multiply(alturaParaImc))));
		StringConverter<? extends Number > converter= new DoubleStringConverter(); //convertidor para leer los valores de los textFields
		Bindings.bindBidirectional(wheightFieldText.textProperty(),pesoParaImc, (StringConverter<Number>)converter);
		Bindings.bindBidirectional(heightFieldText.textProperty(),alturaParaImc, (StringConverter<Number>)converter);
		
		aux=Bindings//grande sergio
				.when(Bindings.isEmpty(heightFieldText.textProperty()).and(Bindings.isEmpty(wheightFieldText.textProperty())))
				.then("(peso*altura^2)")
					.otherwise(Bindings
							.when(alturaParaImc.isEqualTo(0))
							.then("(peso*altura^2)").otherwise(calculoImc.asString()));
		
		formulaImc.textProperty().bind(aux);
		formulaImc.textProperty().addListener(e->{
			if (calculoImc.doubleValue()<18.5)
				clasificacion.setText("bajo peso");
			else if (calculoImc.doubleValue()>=18.5 && calculoImc.doubleValue()<25)
				clasificacion.setText("peso normal");
			else if (calculoImc.doubleValue()>=25. && calculoImc.doubleValue()<30)
				clasificacion.setText("sobrepeso");
			else if (calculoImc.doubleValue()>30)
				clasificacion.setText("obeso");
				
		});

		
	}
	
	private void startWheight () {
		peso = new Label();
		peso.setText("Peso");
		kg= new Label();
		kg.setText("kg");
		wheightFieldText=new TextField();
	}
	private void startHeight() {	
		altura=new Label();
		cm = new Label();
		altura.setText("Altura");
		cm.setText("altura");
		heightFieldText= new TextField();
		
	}
	private void startFormuleLabels() {
		imcLabel=new Label();
		imcLabel.setText("IMC");
		formulaImc=new Label();
	}
	
	private void wheightHbox() {
		wheightHbox=new HBox(5);
		wheightHbox.getChildren().addAll(peso,wheightFieldText,kg);
		wheightHbox.setAlignment(Pos.CENTER);
		
	}
	private void heightHbox() {
		heightHbox=new HBox(5);
		heightHbox.getChildren().addAll(altura,heightFieldText,cm);
		heightHbox.setAlignment(Pos.CENTER);
	}
	private void formuleHBox() {
		formuleHbox=new HBox(5);
		formuleHbox.getChildren().addAll(imcLabel,formulaImc);
		formuleHbox.setAlignment(Pos.CENTER);
	}
	private void startImc() {
		imcVbox=new VBox(5);
		imcVbox.getChildren().addAll(wheightHbox,heightHbox,formuleHbox,clasificacion);
		imcVbox.setAlignment(Pos.CENTER);
		
	}

	public static void main(String[] args) {
		
		launch(args);
		
		}


	}



