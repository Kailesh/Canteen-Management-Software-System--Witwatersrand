<?php 

class QueryProgress
{
	//attributes
	private $statusRequest;
	private $order_status;
	private $order_status_json;
	
	public function decodeStatusRequest($request_json)
	{
		$this->statusRequest = json_decode($request_json);
	}	
	
	public function getStatusOrder()
	{
		$deviceID = $this->statusRequest->deviceID;
		 
		$databaseConnection = Yii::app()->db;
		
		$sql_select = "SELECT *   FROM orders WHERE deviceid = '".$deviceID."'";
		
		$command = $databaseConnection->createCommand($sql_select);
		
		$result = $command->query();
		
		$this->entry = $result->read();
		
		$this->order_status = $this->entry['status'];
	}
	
	public function encodeMsg()
	{
		$this->order_status_json = json_encode($this->order_status);
		return $this->order_status_json;
	}
}

?>