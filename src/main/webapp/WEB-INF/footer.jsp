<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="js/vendor/jquery-1.11.0.min.js"></script>
<script>window.jQuery || document.write('<script src="js/vendor/jquery-1.11.0.min.js"><\/script>')</script>
<script src="js/plugins.js"></script>
<script src="js/main.js"></script>

<!-- Preloader -->
<script type="text/javascript">
  //<![CDATA[
  $(window).load(function() { // makes sure the whole site is loaded
    $('.loader-item').fadeOut(); // will first fade out the loading animation
    $('#pageloader').delay(350).fadeOut('slow'); // will fade out the white DIV that covers the website.
    $('body').delay(350).css({'overflow-y':'visible'});
  })
  //]]>
</script>

</body>
</html>
