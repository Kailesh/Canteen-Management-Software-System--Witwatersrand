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
		var_dump($this->placedOrder->orderNumber);
	}
	
	public function storeOrderIntoDB()
	{
		foreach ($this->placedOrder->basket as $item)
		{
		
			$order = new Orders;
		
			$order->deviceid = $this->placedOrder->deviceID;
			$order->name = $this->placedOrder->name;
			$order->delivery_location = $this->placedOrder->deliveryLocation;
			$order->timeplaced = new CDbExpression('NOW()');
			$order->status = "placed";
		
			$order->item = $item->item;
			$order->quantity = $item->quantity;
			$order->orderNo = $this->placedOrder->orderNumber;
			//$order->station = $item->station;
			$order->save();
		}
		
		/*$order->item = $this->placedOrder->wtf;
		//$order->deliverylocation = $this->placedOrder->shit;
		$order->save();*/
		
	}
}

?>