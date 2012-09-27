<?php 

class PlaceOrder
{
	//attributes
	public $placedOrder_Json;
	public $placedOrder;
	
	//functions
	public function decodePlacedOrder()
	{
		$placedOrder = json_decode($placedOrder_Json);
	}
	
	public function storeOrderIntoDB()
	{
		$order = new Orders;
		
		foreach ($placedOrder as $item)
		{
			$order->deviceid = $item->deviceID;
			$order->item = $item->item;
			$order->quantity = $item->quantity;
			$order->timeplaced = new CDbExpression('NOW()');
			$order->deliverylocation = $item->delivery;
			$order->save();
		}
		
	}
}

?>