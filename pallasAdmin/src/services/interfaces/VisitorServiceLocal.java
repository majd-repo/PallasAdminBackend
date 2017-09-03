package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import persistence.Visitor;

@Local
public interface VisitorServiceLocal {

	public Visitor findVisitorById(Integer id);

	public List<Visitor> findAllVisitors();

	public void addVisitor(Visitor visitor);

	public void saveVisitor(Visitor visitor);

	public void removeVisitor(Integer id);

	public void setPicture(Integer entityId, String fileName);

	public void activateVisitor(Integer id, Boolean active);
}
