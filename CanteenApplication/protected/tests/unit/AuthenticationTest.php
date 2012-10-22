<?php 

class AuthenticationTest extends CDbTestCase
{
	/**
	 * We use 'Authentication'fixtures.
	 * @see CWebTestCase::fixtures
	 */
	
	public $fixtures=array(
			'authenticate'=>':authentication',
	);
	
	public function testCorrectCredentials()
	{
		$authenticate = new Authenticate;
		
		$authenticate_json = json_encode(array("username"=>"kailesh","password"=>"1c3dd8b850b055bb7b6fb0fb59a7cd4","deviceID"=>"90:C1:15:BC:97:4F"));
		
		$authenticate->verifyDetails($authenticate_json);
		
		$expected_response = json_encode(array("access"=>true, "reason"=>"RMB-OK", "balance"=>"5000.00"));
		
		$returned_response = $authenticate->generateJsonResponse();
		
		$this->assertEquals($expected_response,$returned_response);
	}
	
	public function testIncorrectUsername()
	{
		$authenticate = new Authenticate;
	
		$authenticate_json = json_encode(array("username"=>"bob","password"=>"1c3dd8b850b055bb7b6fb0fb59a7cd4","deviceID"=>"90:C1:15:BC:97:4F"));
	
		$authenticate->verifyDetails($authenticate_json);
	
		$expected_response = json_encode(array("access"=>false, "reason"=>"RMB-01", "balance"=>"0"));
	
		$returned_response = $authenticate->generateJsonResponse();
	
		$this->assertEquals($expected_response,$returned_response);
	}
	
	
	public function testIncorrectPassword()
	{
		$authenticate = new Authenticate;
	
		$authenticate_json = json_encode(array("username"=>"kailesh","password"=>"1c3dd8b850b055bb7b6fb0fb59a7cd04","deviceID"=>"90:C1:15:BC:97:4F"));
	
		$authenticate->verifyDetails($authenticate_json);
	
		$expected_response = json_encode(array("access"=>false, "reason"=>"RMB-01", "balance"=>"0"));
	
		$returned_response = $authenticate->generateJsonResponse();
	
		$this->assertEquals($expected_response,$returned_response);
	}
	
	public function testUnregisteredDevice()
	{
		$authenticate = new Authenticate;
	
		$authenticate_json = json_encode(array("username"=>"kailesh","password"=>"1c3dd8b850b055bb7b6fb0fb59a7cd04","deviceID"=>"90:C1:76:AA:97:4F"));
	
		$authenticate->verifyDetails($authenticate_json);
	
		$expected_response = json_encode(array("access"=>false, "reason"=>"RMB-01", "balance"=>"0"));
	
		$returned_response = $authenticate->generateJsonResponse();
	
		$this->assertEquals($expected_response,$returned_response);
	}
	
	public function testUnknownUser()
	{
		$authenticate = new Authenticate;
		
		$authenticate_json = json_encode(array("username"=>"bob","password"=>"1c3dd8b823we055bb7b6fb0fb59a7cd04","deviceID"=>"85:D5:76:AA:97:4F"));
		
		$authenticate->verifyDetails($authenticate_json);
		
		$expected_response = json_encode(array("access"=>false, "reason"=>"RMB-01", "balance"=>"0"));
		
		$returned_response = $authenticate->generateJsonResponse();
		
		$this->assertEquals($expected_response,$returned_response);
	}
	
	
}

?>