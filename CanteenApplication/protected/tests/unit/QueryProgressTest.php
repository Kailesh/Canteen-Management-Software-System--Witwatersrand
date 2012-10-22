<?php 

class QueryProgressTest extends CDbTestCase
{
	/**
	 * We use 'Authentication'fixtures.
	 * @see CWebTestCase::fixtures
	 */
	
	public $fixtures=array(
			//'order'=>':orders',
	);
	
	public function testCorrectProgressRetrieval()
	{
		$queryProgress = new QueryProgress;
		
		$createOrder_json = json_encode(array("deviceID"=>"90:C1:15:BC:97:4F","order"=>1));
		
		$queryProgress->decodeStatusRequest($createOrder_json);
		
		$queryProgress->getStatusOrder();
		
		$returned_response = $queryProgress->encodeMsg();
		
		$expected_response = json_encode(array(array("item"=>"Vegetarian Potjie","status"=>"done"),array("item"=>"Vegetable Lasagne","status"=>"done")));
		
		$this->assertEquals($expected_response,$returned_response);
	}
	
	
}

?>