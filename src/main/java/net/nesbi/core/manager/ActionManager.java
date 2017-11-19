package net.nesbi.core.manager;

import java.util.ArrayList;
import java.util.List;

import net.nesbi.core.actions.Action;

/**
 * Manager for handling actions.
 * The manager is able to add, undo and redo actions
 *
 */
public class ActionManager {

	private List<Action> actions;
	private int actionIndex;

	public ActionManager() {
		clear();
	}

	public void redo() {
		if (actionIndex < actions.size()-1) {
			actionIndex++;
			Action action = actions.get(actionIndex);
			action.execute();
		}
	}

	public void undo() {
		if (actionIndex >= 0) {
			Action action = actions.get(actionIndex);
			action.revoke();
			actionIndex--;
		}
	}
	
	public void addAndExecute(Action action) {
		actions.subList(actionIndex+1, actions.size()).clear();
		actions.add(action);
		action.execute();
		actionIndex++;
	}
	
	public void clear() {
		actions = new ArrayList<Action>();
		actionIndex = -1;
	}
}
