<?php 
class VideoFeed
{
	
	public function getVideoFeed()
	{
		//$url = " http://applications.rmb.co.za/canteencam/NewSnap000M.jpg";
		//$img = file_get_contents($url);
		//return $img;
		
		$images_path = realpath(Yii::app()->basePath . '/../images/live_feed.jpg');
		$img = file_get_contents($images_path);
		return $img;
	}
}
?>