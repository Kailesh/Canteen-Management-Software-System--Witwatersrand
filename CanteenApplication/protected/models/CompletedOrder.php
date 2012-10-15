<?php 

class CompletedOrder extends CFormModel{
	
	public $databaseConnection;
	public $result;
	
	private $_status;
	
	public function extractOrderFromDb()
	{
		$databaseConnection = Yii::app()->db;
		
		$sql_select = "SELECT *   FROM orders WHERE status = 'Done' OR status = 'Delivering' ORDER BY timeplaced DESC";
		
		$command = $databaseConnection->createCommand($sql_select);
		
		$result = $command->query();	
		
		$allOrders = $result->readAll();
		
		return $allOrders;
	}
	
}

?>