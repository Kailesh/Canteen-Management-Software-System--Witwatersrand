<?php 
class LongPoller
{
	//public $newInformationHasCome=false;
	private $statusRequest;
	private $order_status;
	private $order_item;
	private $order_no;
	private $order_done;
    
	public function decodeStatusRequest($request)
	{
		$this->statusRequest = json_decode($request);
	}
	
	public function checkStatus()
	{
		$time = time();
		
		$deviceID = $this->statusRequest->deviceID;
		$this->order_no = $this->statusRequest->orderNo;
			
		$databaseConnection = Yii::app()->db;
		
		$sql_select = "SELECT *   FROM orders WHERE deviceid = '".$deviceID."' AND orderNo=".$this->order_no;
				
		$sql_count = "SELECT COUNT(*)   FROM orders WHERE deviceid = '".$deviceID."' AND orderNo=".$this->order_no;
			
		$command = $databaseConnection->createCommand($sql_select);
		$command1 = $databaseConnection->createCommand($sql_count);
	
		$noOfStatusRaw = $command1->query()->read();
		$noOfStatus = (int) $noOfStatusRaw['COUNT(*)'];
		
		$sendResult = false;
		
		while((time() - $time) < 60) {
    
			// query the specific device id to check the status of the order		
			$result = $command->query();
		
			$this->entry = $result->readAll();
			//var_dump(($this->entry));
			//die;
			
			$noCheckedStatus = 0;
			
			foreach ($this->entry as $item)
			{
				$this->order_status = $item['status'];
				
				// if the order is completed then return it
				if($this->order_status==="Done" or $this->order_status==="Delivering") {
					$noCheckedStatus++;
					//$this->order_done[$noCheckedStaus] = $this->order_status;
					//$this->order_item[$noCheckedStaus] = $item['item'];
					//$this->order_no[$noCheckedStaus] = $item['orderNo'];
				}
				
				if($noCheckedStatus>=$noOfStatus)
				{
					$sendResult = true;
					//var_dump($noCheckedStatus);
					//var_dump($noOfStatus);
					//die;
					break;
				}
			
			}
 
   			if($sendResult===true)
   				break; 
 
    		usleep(25000);
		}
	}
	
	public function generateResponse()
	{
		$responseMsg = json_encode(array("status"=>$this->order_status,"orderNo"=>$this->order_no));
		return $responseMsg;
	}
}
?>