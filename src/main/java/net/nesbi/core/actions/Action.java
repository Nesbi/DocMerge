package net.nesbi.core.actions;
/**
 * Interface for revocable actions
 *
 */
public interface Action {
	public void execute();

	public void revoke();
}
