package pt.ulisboa.tecnico.learnjava.sibs.operation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.ulisboa.tecnico.learnjava.sibs.domain.Operation;
import pt.ulisboa.tecnico.learnjava.sibs.domain.TransferOperation;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;

public class TransferOperationStateMethodTest {
	

	private static final String SOURCE_IBAN = "SourceIban";
	private static final String TARGET_IBAN = "TargetIban";
	private static final int VALUE = 100;

	@Test
	public void stateTest() throws OperationException {
		TransferOperation operation = new TransferOperation(SOURCE_IBAN, TARGET_IBAN, VALUE);

		assertEquals("Registered", operation.getCurrentState());
		operation.process();
		assertEquals("Withdrawn", operation.getCurrentState());
		operation.process();
		assertEquals("Deposited", operation.getCurrentState());
		operation.process();
		assertEquals("Completed", operation.getCurrentState());
		operation.cancel();
		assertEquals("Completed", operation.getCurrentState());
		
		assertEquals(Operation.OPERATION_TRANSFER, operation.getType());
		assertEquals(100, operation.getValue());
		assertEquals(SOURCE_IBAN, operation.getSourceIban());
		assertEquals(TARGET_IBAN, operation.getTargetIban());	
	}
	
	@Test
	public void stateNoFeeTest() throws OperationException {
		TransferOperation operation = new TransferOperation(SOURCE_IBAN, SOURCE_IBAN, VALUE);

		assertEquals("Registered", operation.getCurrentState());
		operation.process();
		assertEquals("Withdrawn", operation.getCurrentState());
		operation.process();
		assertEquals("Completed", operation.getCurrentState());
		
		assertEquals(Operation.OPERATION_TRANSFER, operation.getType());
		assertEquals(100, operation.getValue());
		assertEquals(SOURCE_IBAN, operation.getSourceIban());
		assertEquals(SOURCE_IBAN, operation.getTargetIban());	
	}
	
	@Test
	public void cancelStateTest() throws OperationException {
		TransferOperation operation = new TransferOperation(SOURCE_IBAN, TARGET_IBAN, VALUE);

		assertEquals("Registered", operation.getCurrentState());
		operation.process();
		assertEquals("Withdrawn", operation.getCurrentState());
		operation.cancel();
		assertEquals("Canceled", operation.getCurrentState());
		
		assertEquals(Operation.OPERATION_TRANSFER, operation.getType());
		assertEquals(100, operation.getValue());
		assertEquals(SOURCE_IBAN, operation.getSourceIban());
		assertEquals(TARGET_IBAN, operation.getTargetIban());	
	}

}
