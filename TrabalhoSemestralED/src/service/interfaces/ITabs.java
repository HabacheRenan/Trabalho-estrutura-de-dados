package service.interfaces;

import javafx.scene.layout.BorderPane;

public interface ITabs<T> {
	public BorderPane getTela();

	public T boundaryToEntity();

	public void entityToBoundary(T objeto);
}