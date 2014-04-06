<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"> 
        <meta charset="utf-8">
        <title>Student Management System</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">
         <script type='text/javascript' src="<c:url value="/js/jquery-1.10.2.min.js" />"></script>
        <!-- <link href="<c:url value="/css/bootstrap-glyphicons.css" />" type="text/css" rel="stylesheet"> -->
        <!--[if lt IE 9]>
          <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        
        <!-- CSS code from Bootply.com editor -->
        
        <style type="text/css">
            body {
	padding-top:50px;
}

#masthead {
	min-height:270px;
	background-color:#000044;
  	color:#aaaacc;
}

#masthead h1 {
	font-size: 55px;
	line-height: 1;
}

#masthead .well {
	margin-top:13%;
	background-color:#111155;
  	border-color:#000033;
}

.icon-bar {
	background-color:#fff;
}

@media screen and (min-width: 768px) {
	#masthead h1 {
		font-size: 100px;
	}
}

.navbar-bright {
	background-color:#111155;
    color:#fff;
}
  
.navbar-bright a, #masthead a, #masthead .lead {
  	color:#aaaacc;
}

.navbar-bright li > a:hover {
    background-color:#000033;
}

.affix-top,.affix{
	position: static;
}

@media (min-width: 979px) {
  #sidebar.affix-top {
    position: static;
  	margin-top:30px;
  	width:228px;
  }
  
  #sidebar.affix {
    position: fixed;
    top:70px;
    width:228px;
  }
}

#sidebar li.active {
  	border:0 #eee solid;
  	border-right-width:4px;
}

        </style>
    </head>
    
    <!-- HTML code from Bootply.com editor -->
    
    <body  >
        
        
<header class="navbar navbar-bright navbar-fixed-top" role="banner">
  <div class="container">
    <div class="navbar-header">
      <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a href="#" class="navbar-brand">Student Management System</a>
    </div>
    <nav class="collapse navbar-collapse" role="navigation">
      <ul class="nav navbar-nav">
        <li>
          <a href="https://hub.jazz.net/git/gauravibmtgmc/TGMC13.-.Sharingan">Get Started</a>
        </li>
        <li>
          <a href='<c:url value="/downloadsource"/>'>Downloads</a>
        </li>
        <li>
          <a href='<c:url value="/contact"/>'>Contact</a>
        </li>
      </ul>
    </nav>
  </div>
</header>

<div id="masthead">  
  <div class="container">
      <div class="row">
        <div class="col-md-7">
          <h2>Student Management Console</h2>
            <p class="lead"><font size=-1>The easiest way to manage students records</font></p>
          
        </div>
        <div class="col-md-5">
            <div class="well well-lg"> 
              <div class="row">
                <div class="col-sm-6">
                <img src='<c:url value="/images/7.png" />' height='100px'>  	      	
                </div>
                <div class="col-sm-6">
	              	<strong>Important</strong>
                  	<p>Source Code for the apps now available</p>
                </div>
              </div>
            </div>
        </div>
      </div> 
  </div><!-- /cont -->
</div>

<!-- Begin Body -->
<div class="container">
	<div class="row">
  			<div class="col-md-3" id="leftCol">              
<tiles:insertAttribute name="leftsidebar"/>
      		</div>  
      		<div class="col-md-9">
              	<tiles:insertAttribute name="content"/>
              
              </div> 
  	</div>
</div>



        
       


        <script type='text/javascript' src="<c:url value="/js/bootstrap.min.js" />"></script>



        
        <!-- JavaScript jQuery code from Bootply.com editor -->
        
        <script type='text/javascript'>
        
        $(document).ready(function() {
        
            $('#sidebar').affix({
      offset: {
        top: 245
      }
});

var $body   = $(document.body);
var navHeight = $('.navbar').outerHeight(true) + 10;

$body.scrollspy({
	target: '#leftCol',
	offset: navHeight
});
        
        });
        
        </script>
        
    </body>
</html>