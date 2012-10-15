<?php 

class QueryProgress
{
	//attributes
	private $statusRequest;
	private $order_status;
	private $order_item;
	private $order_status_json;
	
	public function decodeStatusRequest($request_json)
	{
		$this->statusRequest = json_decode($request_json);
	}	
	
	public function getStatusOrder()
	{
		$deviceID = $this->statusRequest->deviceID;
		//var_dump($deviceID);
		$orderNo = $this->statusRequest->order;
		//$orderNo=0;
		//var_dump($orderNo); 
		$databaseConnection = Yii::app()->db;
		
		$sql_select = "SELECT *   FROM orders WHERE deviceid = '".$deviceID."' AND orderNo = " . $orderNo;
		//var_dump($sql_select);
		$command = $databaseConnection->createCommand($sql_select);
		
		$result = $command->query();
		
		$this->entry = $result->readAll();
		//var_dump($this->entry);
		
		$counter = 0;
		
		foreach ($this->entry as $itemStatus)
		{
			$this->order_status[$counter] = array("item"=>$itemStatus['item'],"status"=>$itemStatus['status']);
			$counter++;
		}
		//var_dump($this->order_status);
		
	}
	
	public function encodeMsg()
	{
		$this->order_status_json = json_encode($this->order_status);
		return $this->order_status_json;
	}
}

?>