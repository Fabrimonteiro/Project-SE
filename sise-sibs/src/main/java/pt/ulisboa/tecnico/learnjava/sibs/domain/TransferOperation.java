package pt.ulisboa.tecnico.learnjava.sibs.domain;

import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;

public class TransferOperation extends Operation {
	private final String sourceIban;
	private final String targetIban;
	
	private String currentState;
	

	public TransferOperation(String sourceIban, String targetIban, int value) throws OperationException {
		super(Operation.OPERATION_TRANSFER, value);

		if (invalidString(sourceIban) || invalidString(targetIban)) {
			throw new OperationException();
		}

		this.sourceIban = sourceIban;
		this.targetIban = targetIban;
		this.currentState = "Registered";
	}

	private boolean invalidString(String name) {
		return name == null || name.length() == 0;
	}

	@Override
	public int commission() {
		return (int) Math.round(super.commission() + getValue() * 0.05);
	}

	public String getSourceIban() {
		return this.sourceIban;
	}

	public String getTargetIban() {
		return this.targetIban;
	}
	
	public String getCurrentState() {
		return this.currentState;
	}

	public void process() {
		if (this.currentState == "Registered") {
			this.currentState = "Withdrawn";
		} else if (this.currentState == "Withdrawn" && getBankByCodeIban(this.sourceIban).equals(getBankByCodeIban(this.targetIban))) {
			this.currentState = "Completed";
		} else if (this.currentState == "Withdrawn" && !getBankByCodeIban(this.sourceIban).equals(getBankByCodeIban(this.targetIban))) {
			this.currentState = "Deposited";
		} else if (this.currentState == "Deposited") {
			this.currentState = "Completed";
		}

	}
	
	public void cancel() {
		if (this.currentState != "Completed") {
			this.currentState = "Canceled";
		}
	}
		private String getBankByCodeIban (String iban) {
			return iban.substring(0,3);
		}
}

