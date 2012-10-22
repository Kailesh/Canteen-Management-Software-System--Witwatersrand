<?php 

class MenuTest extends CDbTestCase
{
	/**
	 * We use 'Authentication'fixtures.
	 * @see CWebTestCase::fixtures
	 */
	
	public $fixtures=array(
			//'authenticate'=>':authentication',
	);
	
	public function testMenuRetrieval()
	{
		$menu = new Menu;
		
		$menu->getMenu();
		
		$returned_response = $menu->getJsonString();
		
		$this->assertTrue($returned_response!=Null,'Menu Retrieval Unsuccessful');
	}
	
	public function testMenuUpdate()
	{
		$menu_update = new MenuUpdate;
		
		$menu_update->getMenuStatus();
		
		$expected_response = json_encode(array("menuUpdated"=>true));
		
		$returned_response = $menu_update->encodeResponse();
		
		$this->assertEquals($expected_response,$returned_response);
	}
}

?>