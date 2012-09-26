<?php
$this->pageTitle=Yii::app()->name . ' - CompletedOrders';
$this->breadcrumbs=array(
	'Completed Orders',
);
?>

<?php
// The current page will refresh automatically every 30 seconds.
//header("Refresh: 5;");
?>

<h1>Completed Orders:</h1>
<hr/>

<div class="form">

<?php $form=$this->beginWidget('CActiveForm', array(
	'id'=>'order-form',
	'enableAjaxValidation'=>true,
)); ?>

<div class ="table">
<table>
<tr>
<th>DeviceID</th>
<th>Item</th>
<th> Quantity </th>
<th> Time Placed </th>
<th> Delivery Location </th>
<th> Status </th>
</tr>

<?php foreach($result as $order) :?>

<?php //if($order['status']!='Done' ||  $order['status']=='Deliverying') : ?>	
<tr>
	<td> <?php echo($order['deviceid'])?> 	 		</td>
    <td> <?php echo($order['item'])?>		 		</td> 
	<td> <?php echo($order['quantity'])?> 			</td>
	<td> <?php echo($order['timeplaced'])?> 		</td>
	<td> <?php echo($order['delivery_location'])?> 	</td>
	<td> <?php echo($order['status'])?>    </td>
</tr>
<?php //endif; ?>

<?php endforeach; ?>
</table>

</div>
<?php $this->endWidget(); ?>
</div>