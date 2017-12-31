<?php
include("lock.php");
include ("blocks/bd.php");

/* Если существует в глобальном массиве $_POST['title'] опр. ячейка, то мы создаем простую переменную из неё. Если переменная пустая, то уничтожаем переменную.   */
if (isset($_POST['text']))        {$text = $_POST['text']; if ($text == '') {unset($text);}}
if (isset($_POST['name'])) {$name = $_POST['name']; if ($name == '') {unset($name);}}
if (isset($_POST['descr'])) {$descr = $_POST['descr']; if ($descr == '') {unset($descr);}}
if (isset($_POST['author'])) {$author = $_POST['author']; if ($author == '') {unset($author);}}
if (isset($_POST['dop'])) {$dop = $_POST['dop']; if ($dop == '') {unset($dop);}}
/*if (isset($_POST['id']))      {$id = $_POST['id'];}*/


?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
<title>Обработчик</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<table width="690" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="main_border">
<!--Подключаем шапку сайта-->
<? include("blocks/header.php");   ?>
 <tr>
   <td><table width="690" border="0" cellspacing="0" cellpadding="0">
     <tr>
<!--Подключаем левый блок сайта-->
<? include ("blocks/lefttd.php");  ?>
       <td valign="top">

        <?php
if (isset($name) && isset($text))
{
/* Здесь пишем что можно заносить информацию в базу */
$result = mysql_query ("INSERT INTO article (name,descr,author,dop,text,date) VALUES ('$name','$descr','$author','$dop','$text',NOW())");
if ($result == 'true') {echo "<p>Ваша категория успешно добавлена!</p>";}
else {echo "<p>Ваша категория не добавлена!</p>";}


}
else

{
echo "<p>Вы ввели не всю информацию, поэтому категория в базу не может быть добавлена.</p>";
}


?>


            </td>
     </tr>
   </table></td>
 </tr>
<!--Подключаем нижний графический элемент-->
<?  include ("blocks/footer.php");        ?>
</table>
</body>
</html>