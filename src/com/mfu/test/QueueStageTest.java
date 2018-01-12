package com.mfu.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mfu.entity.queue.Visit;
import com.mfu.entity.queue.VisitTransform;
import com.mfu.web.controller.QueueController;

public class QueueStageTest {
	QueueController controller = new QueueController();
	
	@Test
	public void testGohomeStage_DontHaveDrug(){
		Visit visit = new Visit();
		visit.currenStage = 92;
		visit.drugAcknowledge = 0;
		visit.drugReady = 0;
		visit.drugDispense = 0;
		visit.noneedDrugContractPayment = 1;
		visit.alreadySettleBalance = 1;
		assertEquals(VisitTransform.GO_HOME_STAGE, controller.convertVisitToStage(visit));
	}
	
	@Test
	public void testGohomeStage_HaveDrug(){
		Visit visit = new Visit();
		visit.currenStage = 92;
		visit.drugAcknowledge = 1;
		visit.drugReady = 1;
		visit.drugDispense = 1;
		visit.noneedDrugContractPayment = 0;
		visit.alreadySettleBalance = 1;
		assertEquals(VisitTransform.GO_HOME_STAGE, controller.convertVisitToStage(visit));
	}
	
	@Test
	public void testWaitingDrugStage_Situation1(){
		Visit visit = new Visit();
		visit.currenStage = 92;
		visit.drugAcknowledge = 0;
		visit.drugReady = 0;
		visit.drugDispense = 0;
		visit.noneedDrugContractPayment = 0;
		visit.alreadySettleBalance = 1;
		assertEquals(VisitTransform.WAITING_RECEIVE_DRUG_STAGE, controller.convertVisitToStage(visit));
	}

	@Test
	public void testWaitingDrugStage_Situation2(){
		Visit visit = new Visit();
		visit.currenStage = 92;
		visit.drugAcknowledge = 1;
		visit.drugReady = 0;
		visit.drugDispense = 0;
		visit.noneedDrugContractPayment = 0;
		visit.alreadySettleBalance = 1;
		assertEquals(VisitTransform.WAITING_RECEIVE_DRUG_STAGE, controller.convertVisitToStage(visit));
	}
	
	@Test
	public void testWaitingDrugStage_Situation3(){
		Visit visit = new Visit();
		visit.currenStage = 92;
		visit.drugAcknowledge = 1;
		visit.drugReady = 1;
		visit.drugDispense = 0;
		visit.noneedDrugContractPayment = 0;
		visit.alreadySettleBalance = 1;
		assertEquals(VisitTransform.WAITING_RECEIVE_DRUG_STAGE, controller.convertVisitToStage(visit));
	}
	
	@Test
	public void testWaitingPaymentStage_Situation1(){
		Visit visit = new Visit();
		visit.currenStage = 92;
		visit.drugAcknowledge = 0;
		visit.drugReady = 0;
		visit.drugDispense = 0;
		visit.noneedDrugContractPayment = 0;
		visit.alreadySettleBalance = 0;
		assertEquals(VisitTransform.WAITING_PAYMENT_STAGE, controller.convertVisitToStage(visit));
	}
	
	@Test
	public void testWaitingPaymentStage_Situation2(){
		Visit visit = new Visit();
		visit.currenStage = 92;
		visit.drugAcknowledge = 1;
		visit.drugReady = 0;
		visit.drugDispense = 0;
		visit.noneedDrugContractPayment = 0;
		visit.alreadySettleBalance = 0;
		assertEquals(VisitTransform.WAITING_PAYMENT_STAGE, controller.convertVisitToStage(visit));
	}
	
	@Test
	public void testWaitingPaymentStage_Situation3(){
		Visit visit = new Visit();
		visit.currenStage = 92;
		visit.drugAcknowledge = 1;
		visit.drugReady = 1;
		visit.drugDispense = 0;
		visit.noneedDrugContractPayment = 0;
		visit.alreadySettleBalance = 0;
		assertEquals(VisitTransform.WAITING_PAYMENT_STAGE, controller.convertVisitToStage(visit));
	}
	
	@Test
	public void testVisitDoctorStage(){
		Visit visit = new Visit();
		visit.currenStage = 15;
		visit.drugAcknowledge = 0;
		visit.drugReady = 0;
		visit.drugDispense = 0;
		visit.noneedDrugContractPayment = 0;
		visit.alreadySettleBalance = 0;
		assertEquals(VisitTransform.VISITING_DCOTOR_STAGE, controller.convertVisitToStage(visit));
	}
	
	@Test
	public void testWaitingVisitDoctorStage(){
		Visit visit = new Visit();
		visit.currenStage = 20;
		visit.drugAcknowledge = 0;
		visit.drugReady = 0;
		visit.drugDispense = 0;
		visit.noneedDrugContractPayment = 0;
		visit.alreadySettleBalance = 0;
		assertEquals(VisitTransform.WAITING_VISIT_DOCTOR_STAGE, controller.convertVisitToStage(visit));
	}
	
	@Test
	public void testScreeningStage_Situation1(){
		Visit visit = new Visit();
		visit.currenStage = 7;
		visit.drugAcknowledge = 0;
		visit.drugReady = 0;
		visit.drugDispense = 0;
		visit.noneedDrugContractPayment = 0;
		visit.alreadySettleBalance = 0;
		assertEquals(VisitTransform.SCREENING_STAGE, controller.convertVisitToStage(visit));
	}
	
	@Test
	public void testScreeningStage_Situation2(){
		Visit visit = new Visit();
		visit.currenStage = 14;
		visit.drugAcknowledge = 0;
		visit.drugReady = 0;
		visit.drugDispense = 0;
		visit.noneedDrugContractPayment = 0;
		visit.alreadySettleBalance = 0;
		assertEquals(VisitTransform.SCREENING_STAGE, controller.convertVisitToStage(visit));
	}
	
	@Test
	public void testWaitingScreeningStage(){
		Visit visit = new Visit();
		visit.currenStage = 0;
		visit.drugAcknowledge = 0;
		visit.drugReady = 0;
		visit.drugDispense = 0;
		visit.noneedDrugContractPayment = 0;
		visit.alreadySettleBalance = 0;
		assertEquals(VisitTransform.WAITING_SCREENING_STAGE, controller.convertVisitToStage(visit));
	}
}
