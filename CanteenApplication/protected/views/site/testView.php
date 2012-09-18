<?php
$this->pageTitle=Yii::app()->name . ' - Test';
$this->breadcrumbs=array(
'Test',
);
?>

<h1>Test</h1>

<div class="form">

<?php $form=$this->beginWidget('CActiveForm', array(
'id'=>'test-form',
'enableClientValidation'=>true,
'clientOptions'=>array(
'validateOnSubmit'=>true,
),
)); ?>

<p class="note">Enter test Data.</p>

<div class="row">
<?php echo $form->labelEx($model,'data'); ?>
<?php echo $form->textArea($model,'data',array('rows'=>6, 'cols'=>50)); ?>
<?php echo $form->error($model,'data'); ?>
</div>
<?php echo "you have submitted {$model->data} <br />"; ?>
<?php echo "{$model->result} <br />"; ?>
<div class="row buttons">
<?php echo CHtml::submitButton('Submit'); ?>
</div>

<?php $this->endWidget(); ?>

</div><!-- form -->