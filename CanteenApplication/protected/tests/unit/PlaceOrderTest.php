<?php 

class PlaceOrderTest extends CDbTestCase
{
	/**
	 * We use 'Authentication'fixtures.
	 * @see CWebTestCase::fixtures
	 */
	
	public $fixtures=array(
			'order'=>':orders',
	);
	
	public function testPlaceOrder()
	{
		$placeOrder = new PlaceOrder;
		
		$basket = array(array("item"=>"Hake", "station"=>"A la Minute Grill", "quantity"=>2),array("item"=>"Beef Olives", "station"=>"Main Meal", "quantity"=>3));
		
		$createOrder_json = json_encode(array("deviceID"=>"90:C1:15:BC:97:4F","name"=>"kailesh","total"=>"60.50","deliveryLocation"=>"Floor 3 - Kitchen Side", "orderNumber"=>3,
		"basket"=>$basket));
		
		$placeOrder->decodePlacedOrder($createOrder_json);
		
		$placeOrder->storeOrderIntoDB();
		
		$expected_response = "Orders Recieved";
		
		$returned_response = $placeOrder->response();
		
		$this->assertEquals($expected_response,$returned_response);
	}
	
	public function testPlaceMultipleOrder()
	{
		$placeOrder = new PlaceOrder;
		
		$basket1 = array(array("item"=>"Hake", "station"=>"A la Minute Grill", "quantity"=>2),array("item"=>"Beef Olives", "station"=>"Main Meal", "quantity"=>3));
		
		$createOrder_json1 = json_encode(array("deviceID"=>"90:C1:15:BC:97:4F","name"=>"kailesh","total"=>"60.50","deliveryLocation"=>"Floor 3 - Kitchen Side", "orderNumber"=>3,
				"basket"=>$basket1));
		
		$basket2 = array(array("item"=>"Vegetarian Potjie", "station"=>"Main Meal", "quantity"=>1),array("item"=>"Vegetable Lasagne", "station"=>"Frozen Meal", "quantity"=>1));
		
		$createOrder_json2 = json_encode(array("deviceID"=>"90D8:B3:77:D4:D4:A8","name"=>"patrick","total"=>"25.00","deliveryLocation"=>"Floor 4 - Kitchen Side", "orderNumber"=>1,
				"basket"=>$basket2));
		
		$placeOrder->decodePlacedOrder($createOrder_json1);
		$placeOrder->decodePlacedOrder($createOrder_json2);
				
		$placeOrder->storeOrderIntoDB();
		
		$expected_response = "Orders Recieved";
		
		$returned_response = $placeOrder->response();
		
		$this->assertEquals($expected_response,$returned_response);
	}
	
	
}

?>