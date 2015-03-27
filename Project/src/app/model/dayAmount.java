package app.model;

import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class dayAmount {
	private final ObjectProperty<Date> date;
	private final DoubleProperty amount;
	
	public dayAmount(Date date, double amount) {
		this.amount = new SimpleDoubleProperty(amount);
		this.date = new SimpleObjectProperty<Date>(date);
	}
	
	public void setDate(Date date) {
		this.date.set(date);
	}
	
	public Date getDate() {
		return this.date.get();
	}
	
	public ObjectProperty<Date> dateProperty() {
		return date;
	}
	
	public double getAmount() {
		return this.amount.get();
	}
	public void setAmount(double amount) {
		this.amount.set(amount);
	}
	public DoubleProperty amountProperty() {
		return amount;
	}
	
}
