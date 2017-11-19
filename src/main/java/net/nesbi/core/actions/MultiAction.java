package net.nesbi.core.actions;

import java.util.Collection;

/**
 * Revocable action to combine multiple actions into one
 *
 */
public class MultiAction implements Action{

	private boolean isExecuted = false;
	private Collection<Action> actions;
	
	public MultiAction(Collection<Action> actions) {
		this.actions = actions;
	}
	
	@Override
	public void execute() {
		if (!isExecuted) {
			for(Action action: actions) {
				action.execute();
			}
			isExecuted = true;
		}
	}

	@Override
	public void revoke() {
		if (isExecuted) {
			for(Action action: actions) {
				action.revoke();
			}
			isExecuted = false;
		}		
	}

}
