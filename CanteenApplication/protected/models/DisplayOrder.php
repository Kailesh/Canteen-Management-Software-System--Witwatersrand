<?php 

class DisplayOrder extends CFormModel{
	
	public $databaseConnection;
	public $delivery_status;
	public $order_status;
	public $result;
	
	private $_status;
	
	public function extractOrderFromDb()
	{
		$databaseConnection = Yii::app()->db;
		
		$sql_select = 'SELECT *   FROM orders ORDER BY timeplaced DESC';
		
		$command = $databaseConnection->createCommand($sql_select);
		
		$result = $command->query();	
		
		$allOrders = $result->readAll();
		
		return $allOrders;
	}
	
	public function displayDeliveryStatus()
	{
		return $this->delivery_status;
	}
	
	public function displayOrderStatus()
	{
		return $this->order_status;
	}
}

?>