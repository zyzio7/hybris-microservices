<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <style>
        .login-facebook-btn {
            text-align:center;
            display:block;
        }
        .access-token-info {
            text-align:center;
            display:block;
        }
    </style>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <title>Hybris</title>
</head>

<body>

<div class="container">

    <form class="form-signin">

        <col>
            <div class="row">
                <div class="col-sm-12">
                    <a class="login-facebook-btn" href="https://www.facebook.com/dialog/oauth?client_id=${clientId}&redirect_uri=${redirectUrl}">
                        <img src="http://www.freeiconspng.com/uploads/facebook-sign-in-button-png-26.png" align="middle">
                    </a>
                </div>

                <c:if test="${not empty accesstoken}">
                    <div>
                        <h1 class="access-token-info">About</h1>
                        <h3 class="access-token-info">
                            You have to add this token for every request via rest, it will stop working after some time.
                            <br>To receive new token, use above button again.
                            <br>Thank you
                        </h3>
                    </div>

                    <div class="form-group">
                        <label for="comment">ACCESS-TOKEN:</label>
                        <textarea disabled class="form-control user-token-textarea" rows="5" id="comment">${accesstoken}</textarea>
                    </div>
                </c:if>

            </div>
        </col>

    </form>

</div>

</body>
</html>
