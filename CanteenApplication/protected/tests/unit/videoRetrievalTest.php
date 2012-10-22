<?php 

class videoRetrievalTest extends CDbTestCase
{
	/**
	 * We use 'Authentication'fixtures.
	 * @see CWebTestCase::fixtures
	 */
	
	public $fixtures=array(
			//'order'=>':orders',
	);
	
	public function testVideoRetrieval()
	{
		$videoFeed = new VideoFeed;
		
		$img_returned = $videoFeed->getVideoFeed();
		
		//$image_size = ($img_returned);
		
		$nothingReturned = true;
		
		if(!empty($img_returned))
		{
			$nothingReturned=false;
		}
		
		$this->assertFalse($nothingReturned,'Got the Video Feed');
	}
	
	
}

?>