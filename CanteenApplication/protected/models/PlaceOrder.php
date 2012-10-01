<?php 

class PlaceOrder
{
	//attributes
	public $placedOrder_Json;
	public $placedOrder;
	
	//functions
	public function decodePlacedOrder($placedOrder_Json)
	{
		$this->placedOrder = json_decode($placedOrder_Json);
		var_dump($this->placedOrder);
	}
	
	public function storeOrderIntoDB()
	{
		foreach ($this->placedOrder->basket as $item)
		{
		
			$order = new Orders;
		
			$order->deviceid = $this->placedOrder->deviceID;
			$order->delivery_location = $this->placedOrder->deliveryLocation;
			$order->timeplaced = new CDbExpression('NOW()');
			$order->status = "placed";
		
			$order->item = $item->item;
			$order->quantity = $item->quantity;
			//$order->station = $item->station;
			$order->save();
		}
		
		/*$order->item = $this->placedOrder->wtf;
		//$order->deliverylocation = $this->placedOrder->shit;
		$order->save();*/
		
	}
}

?>