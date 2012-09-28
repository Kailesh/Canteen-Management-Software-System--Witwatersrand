<?php 

class DisplayOrder extends CFormModel{
	
	public $databaseConnection;
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
	
	public function setOrderStatus()
	{
		foreach ($this->result as $item)
		{
			$temp = $item['U_id'];
			$this->order_status[$temp]= $item['status'];
		}
	}
	
	public function updateOrderStatus()
	{
		//create an order class
		$order = new Orders();
		
		//check if the result array is empty or not
		if (is_array($this->result))
		{
			//looping through each order
			foreach ($this->result as $eachItem)
			{
				//find the primary key of each order
				$temp = $eachItem['U_id'];
				//Perform check to ignore completed orders else indexing error occurs
				if ($eachItem['status']==='Processing' or $eachItem['status']==='')
				{			
					if ($this->order_status[$temp] === "Processing")
					{
						$order= Orders::model()->findByPk($temp);
						$order->status = "Processing";
						$order->save();
					}			
					else if ($this->order_status[$temp] === "Done")
					{
						$order= Orders::model()->findByPk($temp);
						$order->status = "Done";
						$order->save();
					}
					else if ($this->order_status[$temp] === "Deliverying")
					{
						$order= Orders::model()->findByPk($temp);
						$order->status = "Deliverying";
						$order->save();
					}
				}
			}
		}
	}
}

?>