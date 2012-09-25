<?php
$this->pageTitle=Yii::app()->name . ' - Orders';
$this->breadcrumbs=array(
	'Orders',
);
?>

<?php
// The current page will refresh automatically every 30 seconds.
//header("Refresh: 5;");
?>

<h1>Incoming Orders:</h1>
<hr />

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

<?php $counter=0;?>
<?php foreach($model->result as $order) :?>

<?php if($order['status']!='Done' ||  $order['status']=='Deliverying') : ?>	
<?php $counter_string= 'item' . (string)$counter;?>
<tr>
	<td> <?php echo($order['deviceid'])?> 	 		</td>
    <td> <?php echo($order['item'])?>		 		</td> 
	<td> <?php echo($order['quantity'])?> 			</td>
	<td> <?php echo($order['timeplaced'])?> 		</td>
	<td> <?php echo($order['delivery_location'])?> 	</td>
	<?php  $status = $order['status'] ?>
	<td> <?php
	if ($order["delivery_location"]!="") {
		echo $form->radioButtonList($model,'delivery_status',array('Processing'=>'Processing','Deliverying'=>'Delivering'),array('onchange'=>CHtml::ajax(array('type'=>'POST', 'url'=>'','success'=>'function(data){$("body").html(data);}')),'separator'=>' | ','labelOptions'=>array('style'=>'display:inline')));
		//'onchange'=>CHtml::ajax(array('type'=>'GET', 'url'=>array("layout/add")
	}
	else {
		echo $form->radioButtonList($model,'order_status',array('Processing'=>'Processing','Done'=>'Done'),array('onchange'=>CHtml::ajax(array('type'=>'POST', 'url'=>'','success'=>'function(data){$("body").html(data);}')),'separator'=>' | ','labelOptions'=>array('style'=>'display:inline')));
	}
	 ?>
    </td>
</tr>
<?php endif; ?>

<?php $counter=$counter+1;?>
<?php endforeach; ?>
</table>

<hr />

<?php echo $form->label($model,'The delivery status is :' . $model->displayDeliveryStatus() . '<br />');?>
<?php echo $form->label($model,'The order status is :' . $model->displayOrderStatus());?>

</div>
<?php $this->endWidget(); ?>
</div>