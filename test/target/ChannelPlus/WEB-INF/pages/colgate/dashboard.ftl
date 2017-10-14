<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(007dd4)http://colgate.alwaysmkt.com.cn/StoreRadar/Dashboard/Dashboard.aspx?d=5002 -->
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Colgate In-Store  Tracking</title> 
<#include "/common/taglibs.ftl" />
<link rel="shortcut icon" type="image/x-icon" href="${contextPath}/img/favicon.ico?cVer=${cVer}">
<body>
<form method="post" action="" id="form1">
    <script type="text/javascript" src="${contextPath}/colgate2/js/jquery-1.8.2.js"></script>
    <script src="${contextPath}/colgate2/js/jquery.scrollTo-min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${contextPath}/colgate2/js/Common.js"></script>
    <script type="text/javascript" src="${contextPath}/colgate2/js/raphael-min.js"></script>
 
    <script src="${contextPath}/colgate2/js/utilities.js" type="text/javascript"></script>
 
    <script src="${contextPath}/colgate2/js/chart.js" type="text/javascript"></script>
    <script src="${contextPath}/colgate2/js/dashboard_revamped.js" type="text/javascript"></script>
    <script src="${contextPath}/colgate2/js/overView.js" type="text/javascript"></script>
    
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/showLoading.css?cVer=${cVer}">
    <script type="text/javascript" src="${contextPath}/js/jquery.showLoading.min.js?cVer=${cVer}"></script>
    <!--[if IE 8]>
<script>
isIE8=1;
</script>
<![endif]-->

<!--[if IE 9]>
<script>
isIE9=1;

</script>
<![endif]-->
    <script type="text/javascript">
      /*function refresh() {
            //$('#btnRefresh').attr('class', 'btnNewRefreshLoading');
            //_dbValidateAndPopulateFilters();
            refreshDashboard
        }*/


        function selectDashboard(btn,id, name) {
            $('#hfDashboardId').val(id.toString());
            $('#hfDashboardName').val(name);
            var val = $('#dbTab input[type=submit].NewMenuButtonSelected');
            for (var i = 0; i < val.length; i++) {
                $(val[i]).attr('class', 'NewMenuButton');
            }
            $(btn).attr('class', 'NewMenuButtonSelected');
            //_dbSelectDashboard(parseInt($('#hfDashboardId').val()), $('#hfDashboardName').val(), true);
            
        }



        $(document).ready(function () {
            $('#btnDBRight').click(function () {
                $('#dbMask').scrollTo('+=502px', 800);
                return false;
            });
            $('#btnDBLeft').click(function () {
                $('#dbMask').scrollTo('-=502px', 800);
                return false;
            });
            dbContentsId = '#dbContents';
           	refreshDashboard();
        });

        
       
    </script>
<link href="${contextPath}/colgate2/css/buttons.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/buttonsCN.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/dashboard.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/DataEntry.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/jquery.lightbox-0.5.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/messageBox.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/overview.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/RBControls.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/report.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/Site.css" type="text/css" rel="stylesheet"></head>
<body style="background-image:none;background-color:White;">
<div class="aspNetHidden">
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="">
<input type="hidden" name="tm_HiddenField" id="tm_HiddenField" value=";;AjaxControlToolkit, Version=3.5.40412.0, Culture=neutral, PublicKeyToken=28f01b0e84b6d53e:zh-CN:065e08c0-e2d1-42ff-9483-e5c14441b311:5546a2b:475a4ef5:d2e10b12:effe2a26:37e2e5c9:5a682656:f9029856:1d3ed089:d1a1d569:497ef277:a43b07eb:751cdd15:dfad98a5:3cf12cf1">
<input type="hidden" name="__LASTFOCUS" id="__LASTFOCUS" value="">
<input type="hidden" name="__SERVER_VIEWSTATE_KEY" id="__SERVER_VIEWSTATE_KEY" value="d15755f6-f9d3-4260-abe0-c794699e2ca7">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">


	<input type="hidden" id="argChainIds" name="argChainIds" value="${filterVo.argChainIds}" />
	<input type="hidden" id="argAccountIds" name="argAccountIds" value="${filterVo.argAccountIds}" />
	<input type="hidden" id="province" name="province" value="${filterVo.province}" />
	<input type="hidden" id="city" name="city" value="${filterVo.city}" />
	<input type="hidden" id="argMonthId" name="argMonthId" value="${filterVo.argMonthId}" />
	<input type="hidden" id="_root" name="_root" value="${contextPath}" />
	<input type="hidden" id="argFilterUserIds" name="argFilterUserIds" value="${filterVo.argFilterUserIds}" />
	<input type="hidden" id="argFilterStructureIds" name="argFilterStructureIds" value="${filterVo.argFilterStructureIds}" />
	<input type="hidden" id="argBrandName" name="argBrandName" value="${filterVo.argBrandName}" />
	<input type="hidden" id="dashboardId" name="dashboardId" value="${filterVo.dashboardId}" >

</div>

<script type="text/javascript">
//<![CDATA[
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}
//]]>
</script>


<script src="${contextPath}/colgate2/css/WebResource.axd" type="text/javascript"></script>


<script src="${contextPath}/colgate2/css/ScriptResource.axd" type="text/javascript"></script>
<script type="text/javascript">
//<![CDATA[
if (typeof(Sys) === 'undefined') throw new Error('ASP.NET Ajax client-side framework failed to load.');
//]]>
</script>

<script src="${contextPath}/colgate2/css/ScriptResource(1).axd" type="text/javascript"></script>
<script src="${contextPath}/colgate2/css/Dashboard.aspx" type="text/javascript"></script>
<div class="aspNetHidden">

	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEdANUGr1Vw9aGBC3r3jK9LxwpmD92FN3JWu7w6FxZpfljg0Guu82k0YZM/xpHlP7DiXV5gSOGLcXRnkC2BYPwWSihwSSJTzpot1aSD0c3IvRKpA7nkaCvUrORNbepBVtXV0Oi2CM31Trsr0OHT6HpJuVaSuQmfqesEM7laazMN3H/fqjf6e4zn7ytAcAWSZjp0GkY5u7SZv7+EyZnMJEb6pR3lcENAJqS4y7keRm/XH70WKiWp8njXrDzmPzQcziRayeWnan2erICdZyer/N1SlT2QPlNxEJZwl+86tilDVSfnguADzQuCw5QKqiCyWSP7ipvvFtaq9ReG7iDGL0rFSkJqVzVGAP6yydihM+RWCF8MguFVDfzr48EYd4bnBqrxZ6eSuaY7F8a4V1BwmYTMqD0yHawWCYAR+WTxFioDRF2Ey0h0t8QwehHu9dJVWLfYqfZCm3yl+9ZVgEVc6ClBWcs3VdRUeyO2HxPVb30jkmi8RuxqOpKkqUWytf1CSw5zaOYRBSu9163YhKpdM8rvmzOn4rW5IRRIqChiBTKO5ccS3xfx5OsKUnWL38spVN6w5x3n5rHpD2IcCp0p0b5LSqMIdD25ic1ZuhDi+LeaQQjfP/04AeN0lRqqKXHzkfhdbXpENu7i3Ks5hLSuHhBPqZB4qencoSesjSsl7wWPPmTqYiKvbW521GqTwSoBzNHuyFFlcWngtDqpCOPJAVW9OnwXsnrCXgn7TMqSJHQpqUxeOjn/owTOfm4qKjIiLoL0lG/XCI+w9Y4XYp1EUZq2yocepgj+wmtutQVBZH3XSUUpi/6tCq+0fRlNwpU7faDoyEAfcG4l0woacYz/IeUhNiX4sDx8TpS15yeKZxObjKdrIkqnIAFShBT6ERj+JECpA8hakoqKpJd0fUSd0Hgk0fYc+e2/skRAwaDT6yBk5Vm2ti+s2NWv7fUluXuH0YJZ1MCG1bGL3e/+Skuy0Zw4LMwvoPWClAeeOdZuW/Q0t6UiM/XaDUXCUOSydJN/5tu67XbLu4bWuoPN9qsvepySNwht0e+jAz6/NVybQg21NQQNtp/GogIXZ23uTMIUFTYA0br8RODoIKAFF9X/XOKDUVLxFB8a/uaJKJFVNEqORQGU4TrbG0Ie3g9+DFSsCf1GcJECU9cjRbBdTSvOQzIqa/xhpSU2bns78h8xVc+WddAjp+FLtrFvZDxB7Gge3WiuLWxpwBlQk4nBpWr64ZFNa1pE9ppOZYJKoV5jA9CTh45n7TPxpfuhKtNUFDpyOc5uyqBqJil5KXITRiFaanMrKPDZxlPX6s+VbKwKlGLMlv3h2aZZXG0fNIC7WzNvZVwT7XN1yLhcG34LLa9DWfw3qUooLYB3vtE6QwzPk7fDEs30RpfAd2WHqbFQkLmohQu/1PZYccEzmsUU2kPAf+VKw7yR9UQuXeuUjQO1goXiGnNARFzwARpd04kly8oxtowApUcMZu2B27RN/O7/8T38vLBb+oueJGI9Q/LzARA+qowDnNjFlUhvD27rWqt4lc7x6h/GVogqwqGeiWWQDIOzoUNsIMAsodlAMk+9yp5nfTbDZ13ls1jLCwRM+zazXSC40pJIWpWmq6ky3ymIudiUypmknRq9TuDixyfI7E+qp6fd5UjZSaR+M650rJ48ow4v7W/1BcymPlQLvwC+ZNMytpVOj+lAbQXk7YkG4Ks1I4OaFYjtVyBkGF0MTXCKRTAlxHf9rV1TmR2nfcjlYxlTHasjeOlRf155d8BwY4Tp1XoNccXMNUOd8Vvx6kdhjz4vpvi0a0eIbv6M5W9LFbtKcIDPegIS2MGqkOWWUkKJAeC/Cvh3oibskJ72ZUNB0n5UXwvNli1X4oIxxcZcICklOaJMA1g4NJxnzTGhxU5tFW56CZGhPXAx05IkB1Dy3Z6V5Y6R0AztbC6EMjr1hJq1qsyRoUQVrZBXz9yx9B8DXXa0kazqJ9tH+59IfNS5ItsaFEJMs/zspJTPQHA8NyrQRMfHZQxN0DbMjIXVHkQDqXU8f13WccscVvvFTsQvdZQ3Ana95jEZGJ73PolOufIrIzEUPafz+dZpRCX4hZuNqsUFLBN+Q343mNdr0mofRONAS4kPe+jK4IIX18FdlSM4qK6F5HLBX70JG3FRaI2nKLvouLMnD3N1U3gDid5eg8WgpOAoBkjBS/kGUJ7FR1c6FWQMq4WaFrOsbPYR8ltPgTbDQsYzs13dl83JG51eQwMTQFCWpzupZMQL7wrfU75MfmBKTFQco0r9MnFpxrIA6w7xpBM1TmRxw7lQi05dOvn1RxxDgBKt+dzMvsOYm0bJE+lD0zN0rXv/TqPniVo83kwuo0bKxz4zpFRoV/DO49i5H8Z0xkCtItySg4a0W+UN2AG6SOhUIQNOZ2fiq43U6NijuyTeDkCcfRC4dL1qbrhqqItR/Ss4mFGNxM7PGtkzfOQrxgr+iMHmG0SqbQG0trLHz4KJXtD3KUMOOvVIdR91fKfnQzi+ppz/5cxB1JM8DOyRyT7TuJmeuC29zqW02q+oBW3Jz9D1C7mrmUdEXIx2cqR9a3KADIK66UKE0bVzIhfuH5WBm1rDOnsHltu82Q1A8QtQjSb/GssDnn+GzzgXD2+Pt50ZblWSVs70b++5ED1ooyccLXWgyc7p8jxh0mZc3/S6vtyyhGMKrZef18I7bvBu6+nIq2lwAj8xp+IHySJ0ZT0iJo6if30GQNHGXCO5ZdK3P9+nXqQ77Tq2bFTW1KC5HoBtQo7tt4rKQxieCGj+BD0ePHZbZqu0nyu3g/8h07/AzFlgK+KYISXvsX3M5PGcJTFgp4yf7Hc22FpE9GnZXKLYVb93Cx05c3GcnlnpaYKF1QWG6rkIuUMvm23ltue36JrQnjrdQpTByqIAv4ZXGLS73n5DoRN/UYlmRKoYCEu374crUCq4klrB/24EavQlivjBj1UOEj2vwGZCld2HcPuvjnDS0oQRjTjr3xk54yG4cA9MWoQyqngwVMwrawlI1jdALGS7tN+5Vd7dzdyuHWtLajf+CUzCIXMBVQB0pl7tqzAfGTkYsdRGCLyrGNAdqvT9hJzE9nAmSxTY5mGAB5u8nZjjBnPQADYlMaFq9YgueimYNmKFBgGZnNxtFSAQQ6mbm/8+2cCmgGIXvrkwFE88v2Ygsvy4NITeI6Ck3SIeJEc2HreJEWFl3yeaQFZZxFORMyvInJXy4IfGntNL/Kx6kbe8BLw4fDl9lXNBhKy32TOpMjfsCSrVewRIPxnouwIpEEv+I4ToPVseVK0ufotpLsbXEvNDz42BzdIAdimCtvaU4e3WIFZ9ASdDjf5c8KFmYEpqKMEKxf+QRLE4BAh4lyZlc4RyRfrXlZfE6hGLkZqzao3RC1aA05wYAgC9SeBURWfvaMdHdiEnPz/J1/dCDrRubVkI5QcxmrhcihpsLMdtry+RL+Q1i0fYsZTQcq+N7iITnIUQ+tOrWlZ4hZaQhyB1JkXdEyUA7LCxTx0i2xPf09Sg6SMB4AUc0VermeORY07QRlLnbr0cf7OE/TpGR7AmXDj1vErSk9f2nN90IO4Ot9HVwCy0Bfh0RX3MzLvgFzUEZNggzDg8faJdtP6UXvXyG1YMPu4rbro/rztU0f08GvOeD7Q6hy9RAkcQ+sErJmOrjS0PXf0WVrQC2/ugaSAIOsyRfLhlVujogzh0ol3S5Kxins2Q8Uo9DI3OxiAa9TyCgzm23e7Vilpv7SfyBR3x35h887fuDU/ZbWjbb6cXUIRzdiIJ7vSs1Ul+y/o8Kx4oxeqFyeW8Z8y3f6hR1mNlidR0P+jOpCXGS/XHrw5mOfUIks/pm0MJBqRshOQ2z2h27Hs8Y04Hm7hBwaeXFkuULyoBOipnWtIqgy7F1BFhfem51CzknY8VSkq3CKDTS6XX1LVSQbMe8r26VJDT+TcBHZ79/dp6kWngQG40tC+rmrIceHvAcdIsRDF38JCzjIea8SJNOpFsoAoEryTmtpZNQGfkjnebYjrccA2MptfmH1GIzTVln2zwnyQepYvY+iv98sQbEL+my448zjHMtMSYVgiqwNymD/wx+i4V1g5UMtjviF+GB8dJbz8feUVwEf4BmKNKxJmrgv9ksLr6DjIEvl8ZD2Wo+gW0aTk63IDQZyOzwYBQyuQ+v0WOmRTgnjFxZbcSSE4AsQuSzKs13JdTdg4Es1yQkf9u9ZECoNuYL8DeppB9lkOaiNwakFrVX99D3MzhynN9qjRHUGDuf4V+qyGNVQ09eQrVucKZDWkXcrYLpfbLVAPrjME/T+M1IpvBFZ6Gr3HuHJNh6d5TQgjInnELY7mWM8Y5IrjCGPidMPX/UpLrrrc9CHYPY88yB1tmxouQx2jXJlWbBs3BR8EAsUuZS7HMQJR/16YT1jeyXEXcJHftuKp/gB0Dx/JG2VOtMyjoY8JJANqkZv6YoQMqtZKavYBEFj3sP0iJkgJh2WsqiyPYHYSWHLO8idKiaDT1gNuAqPBJx5ZgQV/LHqDuJAy5IuqqtLJ6+rGaC8XA7KywR0Nm9rM8D+oMYCQqPJUyg1n1v9S2XBqtRlu7JP4raY3Mc03XPm3z3PMRzcm5EszfaImu/B3nxlZ7AJjIospjmQfIaI6yj7qerGCBZT7T5khZV2mej3hzCq3EzfRa6tSFdLDyTVQ+rlF4u1gIr+6/69QlcVkjFOCFD/GmwGbO7Ld9xPjYSLJNDljmpvA5vHguWEVf8N28oFNVv3Nv/0eFrIxky5/egUny6+TdBc9yPeDYxoEHV7uopvYFAohoxNGXbGOmmkWvIv4zBQwLeEJjBMdjEa4JLsuso9CT2uV1Zrjh/D85qBY2BDBxbXaXubVkSR7JDzDhRPKms5Wq5neM2jZ5gHGsTv/rdLdOSaJ9tPUEWFsqX00cQj0M5d7nqQ7a8C68Boj3uLCNor+Rb+xsRerO0FkyX/B8hbRkglLNHhCdNQcEETIMiOULkh5z/lzb2rLDkNjPGF+pdMDHR2nKJebAs70gLV6oUdm0BBlBRbcHLxvcHRpySfa8jF2zH1uAKe5HvIzHg2li9D4S2oOl2x7caN/MEB3/FaUJZFSfGENexcZekhiHwmBBJ83kHDTQtmOqgR0ZUXmiU7L7pBrX7mDBZpfdzORcm9VF0LAYvbYxmP5KV2Dt6BOx3Aaojc9QmIDoe9tiKN/FMlmo+NjK/YiZ/1sSTpk7Kbs4tiamiEwkxDZMb57qibPUcrqnBYoluKugfjHCnxbWQEuEXtJ927Cu5CVvtHo/751TUF4OPO86egEljvgCdXHgxwFAwWxhY9HvDkmqNkRUnr9rNvedIEXdD4GSK0p7ZEz/DDh+jxhEgW0/05fE8NGJKO2abaPEs7jxTMxJff+QaPIUhMMfWrKpFj6v1+kU1HqHilpQr2WMvT/Flm3SZGBq1N4fmlVHL7a0v8xtnvU26MF5Hum9y+RFB8YH8/vDgc3oWmmQ35R2SFc57k3gu32dXvEP1W/I6j8Lc2acN4ZA8MZBVlaIafIgTjhVBIP1bO0Zmtdesci7e3XfKrYmtHetiwTQIUGdAqDt8FHHZBU9w6YXt6yhpnn41u7ZVfxy0QH6pUaST4GIU5NdbOgPiVUs8utm3XBXLof549dZ4a8YRDVLSMAabuxEKsWxQ/3t2fHLDf/NzwceJTj5YrcAIpipU9KCzP6QewXMZPd+wFLn4oHa0YbMay4qBsRM4s0nrshwI3d1zgZSOCmU0xTkNoYhe01XrNs8SWHgpZwXgzCjw4piY6hJ0IZOguULZbd8Je2hIWx+Av56XR2CdcDSzsdqWhvIbAODSYt3mu9upg03UoHkvvwYdXCweZBjjLH4pkBzWMw3cygs6e3goy0kVSuBQXmfT1FKj019ioP7VEltLYEV4+l/EUm7r2S9sNj84FykvLkj/FhQknpSACzSxiuTDYiwpXubTvs88bHEKttwXfAZ/FT9nnL9JB2gyU+sdBujjCR2Qts8/SbgVz8s9p+fy8uOQKSt7JLdHhs1H42+WRlK/44DYTaPA0tEiq1L9ixmlcBpFU/3XJ2+lktXDMZ7Y2wcg0upDNr01C6roP7MSYE05kqziYLJw3tvHZ9+EWzDcCTr9sgdmlyShY3M7WYoniu1t+RGwuSDAYSGqTh39BDXOTMH1mfIWRYaLiGti0nL1dMQBxsv31wCgjifcdU597ArPFyck2JpapqfbQFg4C7u/UPSyV7S6JsSXURM+OpR9jk6RSbnPbpa+0W6QKpU8RcZzwJaZmCiqg5h7Pbyz1e5pZ8hRb9COwKuG0I+fzQCh9LrhM12C2rpyEqmYJi+fZ7K+7buYva0tPWPOsW6c16I1EKw29TavOjOJiun7FpFvb5HEbQlDU8njVB86mG0zwKQgKVzaurPeR+unVfQbKyIwZrCFTfBYAvp+2wRNnnEDz7zdFQv+KX9aB/cDEcHrQKSxg2MWKDr3SRvH8iQLnmDHDPC06xaD761AwTI3/qCD8CpaZGgLmOyNIZj3Bopw+vzS/Y45Z9nkyh3eaejaXkQF0muMQaXBzJfMP2VWRIK+hba0HiKqzafWQKtkIkht9U+M0b6xXnkA/hCylcRcRNidrypil0MnGSdzX5WHAjrOQBf0ay4Ifbw3AYGt4jKsvQJNtGWhfZ0xdhUpPwZbwUvzJ8cxNDMBOmdGDt4oA/X6JhziZfaaLADK93JpK/pE+Egj8qBWlpzhXS5G8HfM/RQdNeWAOSC7B6BW/egkvtC+2pORoP8f/QimbpO7CrOgYh9kE2woyB0wV7zb+NUw2GMK/l/Jwrd3kFGEPl8FpA/e/KS98RQPUKyIK5a8Xt6GKKQ47Pc4OVxhJJZUkI7dQLziK2LNmtGM3bp1cwq9Nc8gCmP8H0qqXXgqI1kD1uwk6kHvwrMTJJk84WNkMluxDq3pLr2JhXb+JZSbKCHXyREO3IGxurVeObJbHiYamcnet/suzOXbMwVNbw8j3Zvw2TzvM4j/Dr8OvdRnM0mTlyjjYPVidEjsoeUkNYj51MEg1m6tbQ+0Drso5NX1tXcsJRsg2jNGYhI9DYND2VZjsTHvIwV9S6xnJL0n8bTaALwKmlDUkvWwBpdWBLDiiyEoQFgYCbMpu1PIICtrrzQPKFGND35+J7HUGOlc2nU13+iC/wlN0OKv9y/moUMfN6orVrJmn6Ac8/dluYifGfaZsMutC4b5R5KI+zOa4Q9X3hESAzwbUxp+XpTwnnWOnMkWXmxHfOioCEDpzPzefudqgFOa9+0Zbbkg07UOfC1kxW+C3UE5jX7imGtduwhqTIV5ObYNGnumFniz/JCSknbLZlkG82f4XJ48JVJujhnnWqUcjppAv0cb8D35IUvmOkROciSzFtzPfgx3HkYpwV4Z1N8KaazziGu3im91Bmvi2y8sHY262pHlTduWfxg2Msh50vDROQGX+9P/Sc3hQP4o6XBClR53xNxqVEswU789m5BCUsWuJ7VkJ41n0bIRPmfO6Hvkz/vx+wfciPNNWGh/RrR8Kpn7b8bKN9fen4+/mPVeaBuK1ehz1+WBCgpUMog+IOcyQwQWXp0RcSzewIbfy2woW5LTGjUnrQbLcY3owX33F6pxfQRS3Jul3R6pw+R9vKrUxSdB9DWHHmiNrdXGvBGi5MVgTXWPjPdcWtqq12ZSPwZ/gl0RSCZ53yBOBf+oefdIsY3iPQSPQxXuIfeTkoI+Ie0MBhApAY6xGTtkQNb8oPGuPJdRovL5RtFsVIIU6n81pATfyugvf62oAzOmC4a42peR5Z52jX1W3VK0glUWPAsOyI+d8vJUeqkGZRqp+E7F/kVdJZxz5MbNisLUbvlvVQQlU9uty04Q3K4j6x5k4cz8IhjcoaSugqFGOgZHil7YEOe2oMwrNerxfck28Z19CaWo3+hNPgOt2zZnqkd0l9WWdsxbocUXdbYKoIGy0MRRiC42lbbG9qKfQNz4hIdVuK55WNt/rz8i9r/lqbd69iZyoS0m8WmgavRtKLodkpsKdqpC1lumoBBYVdTQqy/jDNV7hO2TueEiKJLSa+VH/xx5+doCxVVdNrpKO2ioP37N1mqdOxN8d2JUMKnMk9Wj8LdXUEkTvHKiv1vbfJMy8uXR+o2ngHXapLtpDhK+6+FOTHNvsrxuDBub4J9NAy+S4ew+WEQw7jFf1y+In4pOZM9bntvQbZOuQvuODWHYUaMWUXcmzfwY2ULaCBipBMxN4jLJ8xaL9kTDZdFx3ENONf4goTM9hoJQGWvvD6cpHY+cLHDNFa6jlL7/0RyuJDg06Wg6+jWVfU+7QD2xbYiYOXd8KPwYGR2PYY3i9LxlUCzBa6JwoqY3j6BoUvM8dDsOk4QN0qoR9RYCEpiL+7UgtQrj6ovp3JR3qDUmuHqBZnjJTnGJcjef8QdstlvioJLUM0s34Er2Udr7D1q7M4KVi5zoMTjj3MFWEOAfJYlwhW+1A+pON0qKhSbrpGgRt9JAUuqxGNjYOLlPYzwrmhkf4qTPZu7o/H1v83pJtIqXu+nBnDZ4qOIawug5tiHeVLeL2jrehYX0ZU9Wwx9sqgBbyRIRYRyhoNB7FQs+4d1U0YvaSvhcO6/L8TuOYg8VpDJ+9VhbOz1hyCbGTZI0iR3j11/m3rHmwaMfay7GDDavqbS75kSv8/t8USa2JfxOo4ETaBbNFsHoHz8HyzouOyepxcTWYbNmha1L+HVAYelredhZqzINaKBuNwgq8unmXFAgRlPmFOz2nT8LTj53mecISmC2pVuPzJ4U94JOmK5DRqnpH03YwlggBe4f9AXa8Cu2SVYxgnhpWbSsfRDOew3sMg3tamvNnRkeYUGhVAL6u+aoDtbmPr6HPTiytkQb4OSrhy4TAm7JC0tjaiqUmCXN1rZqcEsdyfeP7enntBmkWvU7+YZiiSI9m5hYPzbaSYjcgIygcfbPEMlHrvx/qwgIeOHUwbwGAD51UVjMo4Lh8Jmk9VdKH7PXbPDTszIc0qWlXXJ0Q7rfRp4G8myztBZDG5S14pki0g+bCTugYMuhz+7lixqEHCkCchrro8AD9FXtxwZpQRSBx3OB3TNbAqLAv7evYONS4U10bHsKFoQP0CwYJ/XEDc3TQFOOPLPxS6dEhL6uWaWmUTdOY3AlNuu7HsdkTo3qmAc/l/xP1cu6i6D4Mwy0WrVijWSYJb0wdyEidnwW03L7qnp9LeNtjwztLXhJhcPF2BdF00ggd60g+gcOH1GLC1Is6gD2XXJE/fmzq+TkHVsPizuj1+pozxDFJg+TO455fQHkOZAhW/RpFqLfbGOTg2NL79d5bXW5bnl7jGJvD5eusizB3cePChGZxSobI+jRRle1HwQYPxr7Wf21xONAhjCmz/JzDyR/TpdfuVYjG/7aVIuh2YNw+/tKKlGrbCTtZZI7ApSPEe6O/r4nlaak9nj44RQ4mPtysXX+qw+nw+439IB2sRgs+ZjOEs2j2peohy4a3om6979d4z/gAPO2PNEBX+NqcBn14lcJQlyjn1ZiQyoq9Z/AWQj/pJiX72ByX9TMO7rJ2tMB492bAzhfbYXjY5diTMNBGvWN7MjUfNtm/1pTeUq6XvrxfUeEF4OyFr8HJ1/5t9MwKFFw4oEybAAPM0Oi2MAu/q+snAPPrBJgpLFDn7W1veBaWGK+KvV293vUtyVIyFDQ+3n1NABwBSRJ+RmykUtOQzPFUmk0pJX9NVzh4JnrVLl0jpg7gFwyuYoHvzhWie/jknS4llSaw7MByukI0fazIntjXypsZ2v/nFHrSk+b2kpp4nHoD/1m4WrMsTpHyRCPLwVvE/HDnIAyIKUlA8xVPtF36PNUX17NROJkT3agH+NjGPSrQw2YR2eTNxvq4WMoODJa6mNHtAWtZKol2+uV87KG2XYa57eNkAtEgQDdJBXLQuGSaCLmoaLHgR6diwL0Kj6uy5ernj9ui1BVhEmoup5ava4neBydYw/Hta9eLpNrnQeyKvHpABIzh6XJH7Mc8Xy9M+XAsMglVGRewchNg7equSvHx2jlOhzdKJc1vLLvO7sN8Dq9tDUhAmbBq/46Wg68t+6WJ7XPQECR8ebPAoXNLj6lgvGLw/DStGt0jBQvzq5+4ahJyYQpUJMQV883c/SvAJtVHWRwN794jYQi5bn2I2gF78TvzApokOI/RsOtwK1Uw4KQCks5h9h1/OJs2tZJaLsf57kjfyLdv2v/vJEpDD/HN1hO8XguBJVV1YomphNX4G+CII2NwoRBdeqJSG7K8gUe2FTaP/qBlPbOsLf+QhnczJQaLcviZ4Gv6EOBbZBZitt8sgXNR7AInwslfnSyUs4dt8Gq/WjkASBmuQIv0Dax5gKxakOzMCI3KoKr88h5eIeBI32tMp8IbKpBDR8nm04B66od4ny3jbCgykqgjEtWVgL2CMWGng3CvTRqbS48T3cuAkTEcVkHSPPwrlA5P2M3eEa451pfmWuligxCMD8xhEr2clLjEapI0LlR5jrohiT1M14XYfPyNFv1pEoiRzZPDiMgebxNt44HnVa4z0s51m34qHRXBg/JCr/SkS5RP99G/tQ/Yie1rWKWk7geHg8DuRBlF7s0FenNackIjlKyjp5BDrAmSsaxr6CG3zUpthTw7AhdIiEM28DKuJdIWsDUwYnpPF6RXy+WVhaGl8MOVtqqA/tTHDD5br/J8qJQQoWfP3ycSytPEGHKe7yOxR5KYR/2so9F+BdJ39EY4HF3GTya7eP52AwaUxVohvgt5xBCtZ5WCz+eMTvg3fEOZNttyQ7jHLpIAf/4/EUQQM6MFcdE6+0AQ3szQ1+xaI3XqQGhv9Q6+C7ZBAOOb7M4frbIZJrWnW1jsGuY4pjiMBPhngxpO8mRkclPi5Wy64C8dzMK0a4fbkDK2Hd8X5sx9J6QpY0cONDczMxSyXSE8Df+cvAd19Tu+mRo7Ss1UtpVkgle6cwoA04O9ikEld90E38ihUTdGEGDaSekWXhr/tiu2Yun+01JU7O/Wl9t4tSVECvxOSMTj4cABsfJTCPT7sLup/c09y6QtYUtcLrSHMzLmQhchdKefZ2+evYnGr3mgkSKCaANhzC7+cli4XF4wWSzGoLZMC6cQMrcGF+VeTTSO8I7ZZCzBWGst3EOIk4q9D+I0uIIgh9JtItkp8CTqKwbQvU0UABr34lntzbL8nLxuzx+pAGud9kevJSj3dlmMH49jyFKFMAbCuQRnD3L4q+5d1GbxrGe9MA5OqyKHXpR8KeMtWlHgFsnq8UMDO8EDLKjpz0UQk7tBTMcuemaFnghK/5wqtSgxzjvTW9Bl/sBVpL7OWfbcA1ge5wJXwJ6Gz5ayhnXUSkC3ENPOSlmVCiXM8J6nJOj1RZgTumNuWpQjy5UtozfNY5T8BDHTd4gEY5/S9RwZV1YO1dfgv+EAtkagSz2wwnJtT+SDs4SJTfjOnGt/dk0W3x8XEqxjw2CRB7x6oSC7Eq3EMxKKIvRkugRMM0lIT8WBiuwPjLjbTCh5/10KCWC8MHOL7O2SpoPlrj9ZPrzvpFPxSjUj1DwNONIrcstsiND34oQFr2NA5COJtTKo43sKtnnwxaNtyF1rBPaEN7zhxYk4ouQMJG8wxDuD5M0q4+KnzUANoWONHARZOgw5qURjNzlMG9YnMTgFA5KLl2H+lRIAwXzmhsL/dE77b4DRMhU642MWLgkIibsYzMqbtx5ohz8xUQQa0IOBLj2nNXlIcJelEsHUf/PQryGG/QDqnKcttZAQZfWKv0/BR3TH6SQS5i7Cm5mvfgca8Rb54toJOu8QtgsbkWbPzfrAEx0C5kn/nGlUyF9JR9uC5UO8GqC/9VDjBaQ3Gx6eJO+BzUvEbuhwCmkwcfW3r911d3iYV++CxPcl/Bma5esdiqgmDGdPOBZmO7A1zsXfXYILVQH1C6aVkRpOucaSxpP5wT301xvAPJitFDYCK3UOn4v+bqhKvRIgC4bG+u+M0CMQ7l3Ic4h8AdCAUou8z8KxM9w6/lrJsP+a8xjM57jHb8gDXGJtZWRYfkckA2th6NVcLgPq/w0pf5yQv7NcvPIyZ2J5VKpaO27gz2XUCDPsyBsli89QsaxTx/QwvcyIxmDnDSLDfX1tZRfRl/GqyyyASIfh5L5jl7h4AV6z7frZEbU+6Si5aRt1sn3SySZ9wqj7t7QG4iurelf/7ve2hi1OMMzvhw/+b0WqJNqS2YX0Sx7ATiaLKG8+IR/wfE76Lr6Y19LWKeMh+kybEeysJPdAJrM0iJ6Kc+oV9eRWrxqCpHXacLViCY3WgpkTIoDjisQuAbzoSxRgpx9To9qRCYgSOIVi+3BjVJu1IZD32o7EEJO9NFP4k+vQ/aaHNQV12mbcgcNG1NZ77kEYDIuhMT4QU6MScrSoIIUBcmYNn14jCuZ0Qs5qNByEdunVK6xa0/XVDps5mUtEyD/t4eYllHKqAlFP7guC2ZbNBIA3kqkwltZEAvRhr8sQqmSg78fWO7a66cZlTuNoaJI2tdyi+0OX3IZhI9VIlbmbMuWvl0Um1tP8O/4kfFNDVqj8k8RN1oZWr0mzhNbnWts1kJ5ka9nrVTVlEj1UUjMMdsFbha70XZg0+7iWH4jYiEaHJzUYYKwB2FFKqFNsaTQ+A0HiBrnjDhtBnsYcCc+AqxdRNpAGVphizdC0tHE8eAx4YRqfKftsc/P0yE6r5wqnLEZ9lcHnKGU36AcskppDPqlU2MJ9C68bHp3RgVX4f5M6t/jMw2fqii5t3nV3vRSAWPZe2N3v+QbaEGm+doKhMibjNf7JWYP/h5zARUwFo5bfy8MjfvYeOUTEah7rFTVD2+vg2LX2n1MsfqIuOXfmxGlsOTOEiqu+SEqm7uHlh7LILo6Qs+X0SryjHLtta9pYxAp2fGXWuBaMLCKDo8Hzv2sxT8MFLyhgfCp2VPw5oygNIAj/u5rZoUhCpaRt3y1AS0RpM6Nd4TzIz0ZryZ6kiottJ7HL+QHi3/RYRgy7PUO9doPVdbykUWtjmsOQjnpiFsoB/o/5yHy3jXs3lLgEcq3CGCiw0DwsfP+KkAkVbuV0Wb2KkLjl+O79l7Z0FYmZOYieSg2CPwlIuYlWhZSrhsn4JTcZ7UGE84UamggWmgiUHll3mtUupcAGM1DDy8RbL9KRt02KKXFDsThXvwb18Fry94bYbJVRfwwM9B3dyLE9VhZlVjhaVtF7rm65dtbdRoOsTdYPUgJ++WN6a6XEnnr6hW2zgY90DBKTYBhSXaCGJRZ8WcUG17C/2mcXa1ffus9ZIQ/NuQ9IqaaIUERSS7ZazVGN8vUimWogfn4GJlwApuBxtiklG2TvTJwN7kdOZicGXpolZj0C2rP1B8CMIP+TyVbAc7ClY1+gBd6f9ScDmEblkr5jo/4HT73+cTwlXYWm4BQ7rPeIt0nW+JwW+eIPFhMyeCOvMwEsgSkLBMo9I1sGLJoxM0JJjK/9kkppzWQSHQiUKTkr4hBFZPstlRgW0mBqKHxWA0oncYeU4GW2Lze7uRkUdFUZdeCuoo1ChEPQXMJxJvchWwk2LZdxb3o5rL8Wakoo8cQYATK+8QIqKU/rBbDK9B8axI8tnMhzyA4VuZ95iuOsZbr0Uc/gH6zjxcQyqrbnRt1fnemZ4Zi/j9cibWKrUrDbWMb/5Fi+TZ4S7bbKk9dWjMqpwGQgQd0aQOWqS8yGGcaOI9M0O7rwGDf2W7bh8fQdt7UA1l6NNJYJ7XJNQWxJEWBkXyzTFemRm9CS7RuNHGAHygJo3u2MwwbRITnA4az3zPHTYQBgmCnkGWUpL2D6xLMbTPZIPMO8IBAlQTTMuosSezDQn8QZC4ti0ZI57H4j+jlhLSKtWGhcR+zTCUCwR3tqyUWg51fLI/bT7Jaj3VPm0Z3WdpqR5Yn+p2mwxLMgVk5/tdD1WbXhN7YzJ983Re9h/iiRO87tt6jjbEex62PGVNFvzGYbZGndTvCeHxsuyWEkpW3Pl+9xJnZ8UT6WSQ/Ar0TutPpeExRUtvMRna09vuD38UU+lF8HTJaenqCSDofbCwxPaoGqlBymL0VcJYz5dLbroNBINEj7paJW/3y7+cobbco07fd+DQ2arCWhuEExdou/GbonqMiM4Byn8Vhnzq9Uys6/W/xfxg4ntFR18Y+CRbXQzLGp5QFjk6nY4GF5nI5kDlf8R/lz8JnH+p+PXeXFUK1A//ZdO8zKFyB2WZSNnXK38GC683NmHJt1FhAXgsBJyv+SVbgwuwDZYxF6ykd/yjqL3+WQ/faC/76OuZ4CDKbZjgbi4chbr7W8hBiKXYo8y1RXJ6XEeOrgDj9y8JxAUtLMKkAzS9xfKG29MVi0Cm+Hj3jDc3TtwUJ4yxUTAPmC04PeRS1ltDs1A+LG5CiDZMc+tbxG1LAXuUzizakgBfBeACYjWc7XW2Os91YArogIVo8E3yPyidpieqNmsUC25HZEg92goFQ05KE4KPAY66fp6X/wW3v1nb2fOYUNx7IJIDclD+TiV3QowoR+3I6rgSz74T2T6EJQQHU3WPMrpg2tyPR1MTUA3j9Fw7CmSJUvQ1sebx+DqfNDg1JbThhKryieUm6OwiunwLJubUD15xqwaMfo4rK1SgTWlb1Bot1zJVOj7iMyJNWsfAHV73Rcum5kkbG5W/nu76zpjBPzZoQwwvzkrf282ymyD74djDkqGqVcCiVLwd/8wSZ7sXITbCekeWqqStIEs0A+tC4GmP1UQT0A2ao2Qq419rZXevBj48pekpumam/fyQ20/a5ljzsaocE6D5E90VoXeEXJSs1gWLGx3BcfHIOrLvd8ZlD1GlRvJTTOsiQ2Y9NHwqluDsbeiEXf4KruJ/w4N5r0jUV+CFqEzThYgmAwT1S4uWaa9SytJsFf7Z2EMEm4BsEFePmaDxXeph2DG4NH5R1VTA9uDPYZeuIyn3zQifLdJcpFfYK19je/u2+RqyR4CCW76LsUZ1IZrYRPlXJfbaEEWQwSjPlB+3V1Hprf7blpSXgXea1aXGz0WBniv95t28UFvN0XMutKcBHF0J8PGXTYfqxr552Cl46u+kZDtsdGfn1zBTwhxA9a5eczGvBD+ilJdCQzof+zF4o91sZGsp75l+fcxlwDjdS0tnNlvfcYnXmouFAAr8NBM7JVYakUs0QcKd6CaU970RnC72kbAg+4qRd8o1gdHh+MUBZKsBcAPkFEA3rdtSinfjv1KiijCB0m9z+CZSCj/bR1psEloEDxjlhil/TU3bafRyuP1zZIOWmLyk+BLlRn94BD7hEYtR5MZg11DQxzBgvr8lUHs47VToTGlDFuvV8lpaizXLoBkR8XIYBvtUjtL9jocGh+B57YZIslQ+WYPNJvSH3ZsLgJMhE5nLTH46/xjWk384OgOCj7BUE9UI6xW+dK/D6UFVD1gKMpslSVEh+ej4Td3XGLRBFgIR4/1sHcgcx2wch5v5My01NxvHhfDpGrk5wNr/kMJJSpOPrWkSp2QBGMrYGvFiDMUnehoRqBwFx9OpJa3cN4ZqfQaVySSeIohlgZEaIDml9ZPUaicdBYQ5wOVN6QhAIy0e4LhriDc+O9o/2UgcJj9g2ez9Ply0m/RFroV65PASzHmdTHse2FufBxL1AJIR4iyMC/hWyJqER5Rzg9gfXVqV54kF6VrsajhJp0IqF9SE/tdYRwppnJcM1RuSpoFv/zXuvIV0MXe296uvVTv81ML1F/eWIQu6/4CdIM/EwJGaKA/RY/raHnkw+CS0LRI3W+AbeJxW6np0v+IblLbjiIEvE6LshWI8CokLOaCezzAszOnORBUfWMoX53CwuscGtIiE8T10s56ptTkqZ57vfL/msJGtp5uVyUIEIVIt8jG6F09hUZTdXjl5ghOYuwVNIqy3bCvwU6axGXxbWaBaYb5iEeUJfJ9b8SHc/uS0IcPGO8J2Jha1A+tUCA0Dd++qzb1/m6arAszUbbx2Xv/MPxxZvA77LyFYZXTq1YBO1MeaahcTINRJfzMd/169yaMAiBbvcv4vntVmaL/hWpp0FO9RUWXcT7+Kk9Pc56+7IOu8L26WMahrYeTh0+7gosFyx83IJKNbtVkipA26xhmY+uOLXDYMIQHocObwv0jaLPHXPoAsunP5WFtjVGIdSNINnbYaLRvERIejGJLWsX6ZmWuoogqxwAbPkzQ3XitG+LCR43hiAsC48vHg+CnPwtj6xsbxnwLqj+gTUhoIIsIHfePEUdjgH3tpEBOkZH4nBoh1H9GA93XUs+FZ4E76xcuf1LOMyExgAakUu6bFoDCzzrQOanKEzFigVlrJKVgK5Je7i1a0dDx/yzsn4rGF4xVwT7XOXdZBZfLval7E8TVdIdpdfFY/UhRfQJ033trF0NK4zOovFB+onKSRkjpIg6aAIi6vDfKZeoVYFORvn7KX8M6QuOzjVCp4io2jzU2fwOZG01WXz8O57N7MBBh/vAbm/paDMZ7MrZqXAabcTYvpq6J8wsr4H508FQ5zJqfrBFE/wE13F14YFgaXq++Z/4QnJHrNO5j2KdenQISJ5ITU6OQ7ZX4rnJ4DtJ9mhfSG/GUVHVqA9HcCqaC/9N4ViTuOoxLneWaJGEjq4/DnSUPiiK3KJt0nu5QBQK+xbdbGDe4Uzomolscfc66UFrBIv/I0ZnJk5onOixKH9YmVX42HmKXZ1czNvoT117yHFB6+GM3d88Hxnz49LWtVYF6Ly44jCSvUu0atLroQUPc1QLuA9scA4ck3UDejCHYyC1LNyp6bWsTK6eD4s9ePWggwjajblWpM8CrkREN8oaxLbFEKcUTlip72/XlL+jHa1C9bp8a6uQdD9DEEBvU6sgwJhY+ge0TE+jcFFSJ8IN/vipWGQiHq2ETp/fUDuhiKS+vk3EGW5emciq9doru3NCIABczBnjae7rb9TKGD94uh2YajItHjvErKYvwe/sWiQaCC+5fNY+3v6uarpItUZmKVharJUNagQxWWwFLuC6AcEuvKvyQmRnBciPDWLUEgKwuxWFV0RBG/EGcbgjTNJ7hEX4Na1nfd7NmM3OxT+Q12Z48NZA4GfKkBoTWdQkHFge/r7l/F0iorouYa0RbkyaY8hwKBmSOOziCm8oDO5d3kTDlG+pr4cqELUMAnfu7uv2yakcdjI2zyx3o6PSafreyy9hhX9xTL13QZgV156JElMjvOjos+e0tpBiDT+uZ8rnv30p+wyCgBg2GY278LGynOiLHVP0MS1TzozBokgZmtYN1+AmKexF/P/A/LMh1cUsY0ywxtDh2QtQ6upEXRQloeppcHX27jx8zLYXt/VpGHTxUXhGD24P/LRxhDPXn8tC4RPoe92JE5oXMPrzpZHKLCPKB7aahzCYumqMYbYqu9jJZRivIRdhrqgdRJDz530SNCM4t2ZJJTQ4K9ZF8caYbWhxLr62WIcVHDfnwNvc9HGGfYYoLm+6ZriUCCsKwdTEHVUgCeolbiH20ARNYdpeTTMl5Hc9HKoya9jUtjM09Wqu+nsFQbXKanaS8e/fQi/ELAiLmNIeM7KRh4SoDNtP0PCQ/MeJplPbp6Cd05RCq6o5ftCX3c4dzRCfEQCXUH8GZdNSMbM/sHcDl9XeMZBWhg3wquqj6wxgo5bgY0rSVviKbV1sxw4gsdyOUTQ/ebVohQc4evkBR/oKSbf87FUwYepvoz4RwuMQUS0ybZ2FK10Rc2WZn3wcxJqqgz55c0CplN1cWKcj7GfwOsgKAi45T7sDv5w7n41IYsJfI1Qe2jpiF6zLzyNkELM2U7EsC3t5optsarD0tTJlaeNCSFhqAlnj8lZAEeo9isWGvVTduYmodwjGOp3FVP7ZT7g23/W2Zz0Xn8B7c6QMS1a4frt041dyhMFsz/ZtPUFIiITtLYkQYhVXgn+pxABtTt2Gao2nJVjiMx+318tU8o7hJ6w9jDkeNGOfihNcv9tIoDWuLvn8yHRTqzqUPkBlbeX1gTikevyUWYIV15YAoSG3zxZskOVsL75RgoNsZ2CQR1F3LwAnnIMp/G09L6Qxa/W1choOeSOSL+456NMBQ6YH0TSNmWkf3Rq6Opsj1kH4NUo94rVLKRJwATVkMlvhFcfJ2xxxxBl2PE+makqQRA+HN1BxoEuhxMM8QO48CVZN+zrCTgCjVK+t00DriLJTbTt/Air61+n0jJLX3h77piRMjdgVXAMqYC636Fv2+POGWuyRgThCJByLU2gNPzhFrMwGuQJ2FjAGAEk3wE9gaDL5pVzi+7JX/7m5lHEyzo2uyDIaZxtCwz">
</div>
     <script type="text/javascript">
//<![CDATA[
Sys.WebForms.PageRequestManager._initialize('tm', 'form1', ['tupOverview','upOverview','tupAPG','upAPG','tupRegion','upRegion'], [], [], 90, '');
//]]>
</script>

     
<div class="headerNew">
    
   <div style="margin:0px auto;width:960px;">
        <#include "/colgate/colgateHead.ftl" />
    
        </div>
        <div>
            <div style="float:left;">
                <img src="${contextPath}/colgate2/Images/equity_chevron.png">
            </div>
            <div style="float:left;color:#ce1613;font-family:Rockwell,Helvetica, Arial, sans-serif;font-style:italic;font-size:20px;margin:15px 0px 0px 10px;">
                In-Store Tracking
            </div>
            
            <div style="float:right;margin:23px 0px 0px 0px;text-align:right;width:540px;">
                <table width="100%" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                        <td>
                            <a targer="_self" href="${contextPath}/colgate/home/"><input type="button" id="ucHeader_btnHome" class="btnHome"></input></a>
                        </td>
                        
                                <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                                    <input type="button" value="Overview" onclick="window.location.href='${contextPath}/colgate/overView/'" class="NewMenuButton">
                                </td>
                            
                                <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                                     <input type="button" value="Dashboard" onclick="window.location.href='${contextPath}/colgate/dashboard/'" class="NewMenuButtonSelected"></input>
                                </td>
                            
                                <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                                    <input type="button" value="Reports" onclick="window.location.href='${contextPath}/colgate/reports/'" class="NewMenuButton"></input>
                                </td>
                                
                                <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                                     <input type="button" value="RAW DATA" onclick="window.location.href='${contextPath}/colgate/rawdata/'" class="NewMenuButton"></input>
                                </td>
                                
                                <@shiro.hasPermission name="C5M5">
									 <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                                    	<input type="button" value="Gallery" onclick="window.location.href='${contextPath}/colgate/gallery/'" class="NewMenuButton"></input>
                                	</td>
								</@shiro.hasPermission>
                                
                               
                    </tr>
                </tbody></table>
               
            </div>
            
        </div>
   </div>
   
</div>
    <div class="page">
       
        <div class="content">
            <input type="hidden" name="hfDashboardId" id="hfDashboardId" value="5002">
            <input type="hidden" name="hfDashboardName" id="hfDashboardName" value="Distribution">
            
            <div>
                
                <div style="float:right;margin:0px 5px 15px 0px;">
                    <table border="0" cellpadding="0" cellspacing="0" id="dbTab">
                        <tbody><tr>
                            <td style="padding-right:10px;">
                                <div style="width:9px;">
                                    <input type="button" id="btnDBLeft" class="btnDBLeft">
                                </div>
                                    
                            </td>
                            <td>
                                <div style="width:707px;overflow:hidden;" id="dbMask">
                                    <div style="width:1334px;" id="ddt">
                                        <input id="distriBut" type="button" name="dashboardItems" data="5002" value="Distribution" class="NewMenuButtonSelected" style="font-size:12px;">
                                        <input id="shareBut" type="button" name="dashboardItems" data="5004" value="Share of Shelf" class="NewMenuButton"  style="font-size:12px;">
                                        <input id="skuBut"type="button" name="dashboardItems" data="5006" value="SKU ON Shelf" class="NewMenuButton" style="font-size:12px;">
                                        <input id="priceBut" type="button" name="dashboardItems" data="5003" value="Price (vs RRSP)" class="NewMenuButton" style="font-size:12px;">
                                        
                                    </div>
                                </div>
                                <script type="text/javascript">
                                	if("share" == "${type}" || "SHARE" == "${type}"){
										$("#shareBut").removeClass("NewMenuButton");    
										$("#shareBut").addClass("NewMenuButtonSelected");   
										
										$("#distriBut,#skuBut,#priceBut").removeClass("NewMenuButtonSelected");
										$("#distriBut,#skuBut,#priceBut").addClass("NewMenuButton");
                                	}else if("bbn" == "${type}" || "bbn" == "${type}"){
                                		$("#distriBut").removeClass("NewMenuButton");
                                		$("#distriBut").addClass("NewMenuButtonSelected");
                                		
										$("#skuBut,#priceBut,#shareBut").removeClass("NewMenuButtonSelected");
										$("#skuBut,#priceBut,#shareBut").addClass("NewMenuButton");
                                	}else if("rrsp" == "${type}" || "rrsp" == "${type}"){
                                		$("#priceBut").removeClass("NewMenuButton");
                                		$("#priceBut").addClass("NewMenuButtonSelected");
										
										$("#skuBut,#distriBut,#shareBut").removeClass("NewMenuButtonSelected");
										$("#skuBut,#distriBut,#shareBut").addClass("NewMenuButton");
                                	}else if("sku" == "${type}" || "sku" == "${type}"){
                                		$("#skuBut").removeClass("NewMenuButton");
                                		$("#skuBut").addClass("NewMenuButtonSelected");
                                		
                                		$("#priceBut,#distriBut,#shareBut").removeClass("NewMenuButtonSelected");
										$("#priceBut,#distriBut,#shareBut").addClass("NewMenuButton");
                                	}
                                </script>
                            </td>
                            <td style="padding-left:10px;">
                                <div style="width:9px;">
                                    <input type="button" id="btnDBRight" class="btnDBRight">
                                </div>
                                    
                            </td>
                        </tr>
                    </tbody></table>
                </div>
                <div style="clear:right;"> </div>
                
            </div>
            
            <div>
                <div class="sideFilterPanel">
                   
                            <div class="sideFilterPanelTop" style="padding-top: 12px;">
                                <span id="lblDashboardFilterTitle">Distribution</span>
                            </div>
                            <div class="sideFilterPanelMid">
                                <div style="padding: 0px 0px 5px 0px; text-align: center;">
                                    <div style="font-size: 14px; font-weight: bold;">
                                        # STORES AUDITED
                                    </div>
                                    <div style="padding: 0px 0px 0px 0px; text-align: center; font-weight: bold; font-size: 60px;">
                                        <span id="lblNumOfStores"></span>
                                    </div>
                                </div>
                                <div id="upOverview">
	
                                    <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_cbCategory">
                                        
<div class="RBDropdownList">  
    <table id="cbCategory_ddl" border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
			<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                <div class="filterIconCategory">
                </div>
            </td>
			<td class="midColumn">
                <div class="selectedData">
                    <span id="lblSelectedcbCategory">All Categories</span>
                </div>
            </td>
			<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                <div id="cbCategory_dropdownIcon">
				
                    <div class="filterIconArrow">
                    </div>
                
			</div>
            </td>
		</tr>
		</tbody>
	</table>
	
    
    <div id="cbCategory_PopupMenu" class="RBDropdownCheckboxListItems" style="visibility: hidden; position: absolute; left: 493px; top: 327px; z-index: 1000; display: none;">
        <div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tbody><tr>
                    <td style="width:15px;" align="right">
                                
                    </td>
                    <td align="left">
            <table id="cbCategory_Items" class="RBDropdownCheckboxListItem">
			<tbody>
				<tr>
					<td>
						<span class="rbDdlCB">
							<input id="arg_categorys_but_1" type="checkbox" name="argCategorys" value="1" checked="checked">
							<label for="arg_categorys_but_1">Toothpaste</label>
						</span>
					</td>
				</tr><tr>
					<td>
						<span class="rbDdlCB">
							<input id="arg_categorys_but_2" type="checkbox" name="argCategorys" value="2" checked="checked">
							<label for="arg_categorys_but_2">Toothbrush</label>
						</span>
					</td>
				</tr><tr>
					<td>
						<span class="rbDdlCB">
							<input id="arg_categorys_but_3" type="checkbox" name="argCategorys" value="3" checked="checked">
							<label for="arg_categorys_but_3">Mouthwash</label>
						</span>
					</td>
				</tr>
			</tbody>
		</table>
                    </td>
                </tr>
            </tbody>
            </table>
        </div>
	</div>
</div>
                                    </div>
                                    <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_rbBrand">
									<div class="RBDropdownList" id="brands_List">
									            <table id="rbBrand_ddl" border="0" cellpadding="0" cellspacing="0">
													<tbody><tr>
														<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
											                        <div class="filterIconBrand">
											                        </div>
											                    </td>
														<td class="midColumn">
											                        <div class="selectedData">
											                            <span id="lblSelectedrbBrand">All Brands</span>
											                        </div>
											                    </td>
														<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
											                <div id="rbBrand_dropdownIcon">
															<div class="filterIconArrow"></div>
											                        
														</div>
											            </td>
													</tr>
												</tbody>
											</table>
									            <div id="rbBrand_PopupMenu" class="RBDropdownListItems" style="display: none; visibility: hidden; position: absolute;">
									                <div>
									                    <input type="button" data="0" name="arg_brand_name_but" value="All Brands" id="rbBrand_rpItems_Item_0" class="RBDropdownListItem"></input>
									                </div>
									                <div>
									                    <input type="button" data="1" name="arg_brand_name_but" value="Colgate" id="rbBrand_rpItems_Item_1" class="RBDropdownListItem">
									                </div>
									                <div>
									                    <input type="button" data="2" name="arg_brand_name_but" value="Key Competitors Brands" id="rbBrand_rpItems_Item_2" class="RBDropdownListItem">
									                </div>
									            </div>
									    	</div>
									</div>
                                    
                                    
                                    
                                    
                                    <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_cbChannel">
                                        
<div class="RBDropdownList">    
        
    <input type="hidden" name="cbChannel$hfTotalNoOfItems" id="cbChannel_hfTotalNoOfItems" value="4">
    <input type="hidden" name="cbChannel$hfSelectedValue" id="cbChannel_hfSelectedValue">
    <input type="hidden" name="cbChannel$hfSelectedText" id="cbChannel_hfSelectedText">   
    <table id="cbChannel_ddl" border="0" cellpadding="0" cellspacing="0">
		<tbody><tr>
			<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                <div class="filterIconChannel">
                </div>
            </td>
			<td class="midColumn">
                <div class="selectedData">
                    <span id="lblSelectedcbChannel">All Channels</span>
                </div>
            </td>
			<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                <div id="cbChannel_dropdownIcon">
				
                    <div class="filterIconArrow">
                    </div>
                
			</div>
            </td>
		</tr>
	</tbody></table>
	
    
    <div id="cbChannel_PopupMenu" class="RBDropdownCheckboxListItems" style="display: none; visibility: hidden; position: absolute;">
		
        
        <div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tbody id="channel_tbody">
            	</tbody>
            
            
            </table>
                    
                    
        </div>
            
         
    
	</div>
        
    
</div>
                                    </div>
                                    <div id="upAPG">
		
                                            <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_ddlKeyAccount">
                                                
<div class="RBDropdownList">
               
            <input type="hidden" name="ddlKeyAccount$hfSelectedValue" id="ddlKeyAccount_hfSelectedValue" value="0">
            <input type="hidden" name="ddlKeyAccount$hfSelectedText" id="ddlKeyAccount_hfSelectedText" value="All APG">
            <table id="ddlKeyAccount_ddl" border="0" cellpadding="0" cellspacing="0">
			<tbody><tr>
				<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div class="filterIconAPG">
                        </div>
                    </td>
				<td class="midColumn">
                        <div class="selectedData">
                            <span id="lblSelectedddlKeyAccount">All APG</span>
                        </div>
                    </td>
				<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div id="ddlKeyAccount_dropdownIcon">
					
                            <div class="filterIconArrow">
                            </div>
                        
				</div>
                    </td>
			</tr>
		</tbody></table>
		
            
            <div id="ddlKeyAccount_PopupMenu" class="RBDropdownListItems" style="visibility: hidden; position: absolute; left: 501px; top: 391px; z-index: 1000; display: none;">
            	<div>
                	<div><input type="button" name="argChainIds_but" data="" value="All APG" id="ddlKeyAccount_rpItems_Item" class="RBDropdownListItem"></div>
                </div>
			</div>
        
    
</div>



                                            </div>
                                            <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_ddlCustomer">
                                                
<div class="RBDropdownList">
               
            <input type="hidden" name="ddlCustomer$hfSelectedValue" id="ddlCustomer_hfSelectedValue" value="0">
            <input type="hidden" name="ddlCustomer$hfSelectedText" id="ddlCustomer_hfSelectedText" value="All Accounts">
            <table id="ddlCustomer_ddl" border="0" cellpadding="0" cellspacing="0">
			<tbody><tr>
				<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div class="filterIconCustomer">
                        </div>
                    </td>
				<td class="midColumn">
                        <div class="selectedData">
                            <span id="lblSelectedddlCustomer">All Accounts</span>
                        </div>
                    </td>
				<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div id="ddlCustomer_dropdownIcon">
					
                            <div class="filterIconArrow">
                            </div>
                        
				</div>
                    </td>
			</tr>
		</tbody></table>
		
            
            <div id="ddlCustomer_PopupMenu" class="RBDropdownListItems" style="visibility: hidden; position: absolute; left: 493px; top: 507px; z-index: 1000; display: none;">
						<input type="button" name="argAccountIds_but" data="" value="All Accounts" id="ddlCustomer_rpItems_Item" class="RBDropdownListItem">
		</div>
        
    
</div>



                                            </div>
                                        
	</div>
                                    <div id="upRegion">
		
                                            <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_cbRegion">
                                                
<div class="RBDropdownList">    
        
    <input type="hidden" name="cbRegion$hfTotalNoOfItems" id="cbRegion_hfTotalNoOfItems" value="4">
    <input type="hidden" name="cbRegion$hfSelectedValue" id="cbRegion_hfSelectedValue">
    <input type="hidden" name="cbRegion$hfSelectedText" id="cbRegion_hfSelectedText">   
    <table id="cbRegion_ddl" border="0" cellpadding="0" cellspacing="0">
			<tbody><tr>
				<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                <div class="filterIconRegion">
                </div>
            </td>
				<td class="midColumn">
                <div class="selectedData">
                    <span id="lblSelectedcbRegion">All Regions</span>
                </div>
            </td>
				<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                <div id="cbRegion_dropdownIcon">
					
                    <div class="filterIconArrow">
                    </div>
                
				</div>
            </td>
			</tr>
		</tbody></table>
		
    
    <div id="cbRegion_PopupMenu" class="RBDropdownCheckboxListItems" style="visibility: hidden; position: absolute; left: 493px; top: 552px; z-index: 1000; display: none;">
			
        
        <div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tbody><tr>
                    <td style="width:35px;" align="right">
                                
                    </td>
                    <td align="left">
                        <table id="cbRegion_Items" class="RBDropdownCheckboxListItem">
				<tbody id="cbRegion_PopupMenu_tbody">
				
			</tbody></table>
                                
                    </td>
                </tr>
            </tbody></table>
                    
                    
        </div>
            
         
    
		</div>
        
    
</div>
                                            </div>
                                            <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_ddlProvince">
                                                
<div class="RBDropdownList">
               
            <input type="hidden" name="ddlProvince$hfSelectedValue" id="ddlProvince_hfSelectedValue" value="0">
            <input type="hidden" name="ddlProvince$hfSelectedText" id="ddlProvince_hfSelectedText" value="All Provinces">
            <table id="ddlProvince_ddl" border="0" cellpadding="0" cellspacing="0">
			<tbody><tr>
				<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div class="filterIconProvince">
                        </div>
                    </td>
				<td class="midColumn">
                        <div class="selectedData">
                            <span id="lblSelectedddlProvince">All Provinces</span>
                        </div>
                    </td>
				<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div id="ddlProvince_dropdownIcon">
					
                            <div class="filterIconArrow">
                            </div>
                        
				</div>
                    </td>
			</tr>
		</tbody></table>
		
            
            <div id="ddlProvince_PopupMenu" class="RBDropdownListItems" style="display: none; visibility: hidden; position: absolute;">
            
				</div>
        
    
</div>



                                            </div>
                                            <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_ddlCity">
                                                
<div class="RBDropdownList">
               
            <input type="hidden" name="ddlCity$hfSelectedValue" id="ddlCity_hfSelectedValue" value="0">
            <input type="hidden" name="ddlCity$hfSelectedText" id="ddlCity_hfSelectedText" value="All Cities">
            <table id="ddlCity_ddl" border="0" cellpadding="0" cellspacing="0">
			<tbody><tr>
				<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div class="filterIconCity">
                        </div>
                    </td>
				<td class="midColumn">
                        <div class="selectedData">
                            <span id="lblSelectedddlCity">All Cities</span>
                        </div>
                    </td>
				<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div id="ddlCity_dropdownIcon">
					
                            <div class="filterIconArrow">
                            </div>
                        
				</div>
                    </td>
			</tr>
		</tbody></table>
		
            
            <div id="ddlCity_PopupMenu" class="RBDropdownListItems" style="display: none; visibility: hidden; position: absolute;">
			
            
			</div>
        
    
</div>



                                            </div>
                                        
	</div>
                                    
                                
</div>
                                <div style="text-align: center; padding-top: 25px; padding-bottom: 100px;">
                                    <input type="button" name="btnRefresh2" onclick="refreshDashboard();" id="btnRefresh2" class="btnNewRefresh">
                                </div>
                            </div>
                            <div class="sideFilterPanelBtm">
                            </div>
                        
                </div>
                <div class="overviewPanel">

                    
<script type="text/javascript">
    
    function btnPeriodClicked(btn, period) {
        $('#ddlPeriod_hfSelectedValue').val(period);
        var val = $('#div_ddlPeriod input[type=submit].btnPeriodSelected');
        for (var i = 0; i < val.length; i++) {
            $(val[i]).attr('class', 'btnPeriod');
        }
        $(btn).attr('class', 'btnPeriodSelected');
        //refresh();
    }
    $(document).ready(function () {
        $('#btnPeriodRight').click(function () {
            $('#periodMask').scrollTo('+=232px', 800);
            periodClickCounter = periodClickCounter + 1;
            return false;
        });
        $('#btnPeriodLeft').click(function () {
            if (periodClickCounter > 0) {
                $('#periodMask').scrollTo('-=232px', 800);
                periodClickCounter = periodClickCounter - 1;
            }
            return false;
        });
        $('#periodMask').scrollTo('max', 800);

    });
</script>
<div style="padding-top: 12px; margin-bottom: 30px;background-color:White;" id="div_ddlPeriod">
        <input type="hidden" name="ddlPeriod$hfSelectedValue" id="ddlPeriod_hfSelectedValue" value="Jul 2015">
        <table border="0" cellpadding="0" cellspacing="0" id="periodTab">
            <tbody><tr>
                <td style="padding-right:10px;">
                    <div style="width:12px;">
                        <input type="button" id="btnPeriodLeft" class="btnPeriodLeft">
                    </div>
                                    
                </td>
                <td>
                    <div style="width:696px;overflow:hidden;" id="periodMask">
                        <div id="divCon" style="width: 8120px; min-width: 696px;">
                            <#list months as m>
								 <input type="button" name="ddlPeriod2" data="${m.monthId}" value="${m.name}" class="<#if filterVo.argMonthId==m.monthId>btnPeriodSelected<#else>btnPeriod</#if>">
                            </#list>
                        </div>
                    </div>
                </td>
                <td style="padding-left:10px;">
                    <div style="width:12px;">
                        <input type="button" id="btnPeriodRight" class="btnPeriodRight">
                    </div>
                                    
                </td>
            </tr>
        </tbody></table>
                        
                       
    </div>

                    <div id="dbContents" style="padding-left: 20px; display: block;">
                    </div>
                     <div id="dashboardLoading" style="text-align: center; display: none;">
                        <img alt="loading" src="${contextPath}/colgate2/Images/loader.gif">
                    </div>
                </div>
                <div style="clear: both;">
                </div>
            </div>
        </div>
         
<div class="footerNew">
	<#include "/colgate/clogateFooter.ftl"/>
</div>

    </div>
    <input type="button" name="msg_btn" value="" id="msg_btn" style="display:none;visibility:hidden;"><div id="msg_pnl" class="modalPopupMessageBox" style="display: none; position: fixed;">
	<div class="modalMessageHeader"><table width="100%" border="0"><tbody><tr><td><span class="Title" id="msglblHeader"></span></td></tr></tbody></table></div><div class="modalMessageBody"><div class="modalMessageContent"><span id="msgltMessage"></span></div> <div id="msg__divConfirm"><table border="0" cellspacing="0" cellpadding="0" width="100%"><tbody><tr><td align="center"><input type="button" class="buttonYes" onclick_me="__YesButton_Clicked();return false;">&nbsp;<input type="button" class="buttonNo" onclick_me="__NoButton_Clicked();"></td></tr></tbody></table></div><div id="msg__divAlert"><input type="button" class="buttonOk" onclick_me="__OkButton_Clicked();"></div><div id="msg__divContinue"><input type="button" class="buttonContinue" onclick_me="__ContinueButton_Clicked();"></div></div><div class="modalMessageHeaderXButtonContainer" align="right" style="position:absolute;top:0px;right:0px;"><input class="buttonX" onclick_me="__XButton_Clicked();return false;" type="button"></div>
</div><input type="button" name="lbi_btn" value="" id="lbi_btn" style="display:none;visibility:hidden;"><div id="lbi_pnl" class="modalPopupIF" style="display: none; position: fixed;">
	<div class="modalIFHeader"><table width="100%" cellspacing="0" cellpadding="0" border="0"><tbody><tr><td class="left"></td><td class="middle" align="left"><div id="__ifHeaderImgTitle" class=""></div></td><td class="middle" align="right"></td><td class="right"></td></tr></tbody></table></div><iframe id="__iFModal" allowtransparency="true" style="background-color:Transparent;" scrolling="no" frameborder="0" marginheight="0" marginwidth="0"></iframe><div class="modalMessageHeaderXButtonContainer" align="right" style="position:absolute;top:0px;right:0px;"><input type="button" class="buttonX" onclick_me="__HideLB_Frame(&#39;&#39;);return false;"></div>
</div><input type="button" name="lb_btn" value="" id="lb_btn" style="display:none;visibility:hidden;"><div id="lb_pnl" class="modalPopupLoading" style="display: none; position: fixed;">
	<div><table width="100%" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td align="right"><img src="${contextPath}/colgate2/css/WebResource(1).axd"></td><td>&nbsp;</td><td align="left"><span class="loading">This page is Loading, please wait....</span></td></tr></tbody></table></div>
</div>

<script type="text/javascript">
//<![CDATA[
var __callback_LB;var __callback_LB_NoButton;var __curMode_LB;var __callBackDelay = 10;var __callBackBtnId;var __countDownTimer=0;var __countDownMsg;var __countDownTimeoutMsg;var __countDownObj;function __YesButton_Clicked() {$find("msg_mpeMessage").hide();if( __callback_LB && __callback_LB.length > 0 ) {setTimeout("__evalCallBack_LB()", __callBackDelay);} else {var prm = Sys.WebForms.PageRequestManager.getInstance();prm._doPostBack(__callBackBtnId, '');__callBackBtnId="";}}function __ContinueButton_Clicked() {$find("msg_mpeMessage").hide();__polling('SSU');if(__countDownObj){clearTimeout(__countDownObj);}}function __OkButton_Clicked() {$find("msg_mpeMessage").hide();setTimeout("__evalCallBack_LB()", __callBackDelay);}function __XButton_Clicked() {$find("msg_mpeMessage").hide();if( __curMode_LB == 0) {setTimeout("__evalCallBack_LB()", __callBackDelay);}else if(__curMode_LB==2){__pollingStart(30000);if(__countDownObj){clearTimeout(__countDownObj);}}}function __evalCallBack_LB() {eval(__callback_LB);__callback_LB = "";}function __evalCallBack_LB_No() {eval(__callback_LB_NoButton);__callback_LB_NoButton = "";}function __NoButton_Clicked() {$find("msg_mpeMessage").hide();if( __callback_LB_NoButton && __callback_LB_NoButton.length > 0 ) {setTimeout("__evalCallBack_LB_No()", __callBackDelay);}}function ShowMessageLBDelay(title, msg, bid, mode, callback) {if($find("msg_mpeMessage")) {ShowMessageLB(title, msg, bid, mode, callback);}else{setTimeout(function(){ShowMessageLBDelay(title, msg, bid, mode, callback)}, 20);}}function UpdateCountDown(){var tmp = __countDownMsg.replace('[TIMER]', __countDownTimer);$get("msgltMessage").innerHTML = tmp;__countDownTimer--; if( __countDownTimer >= 0 ){__countDownObj = setTimeout('UpdateCountDown()', 1000);}else {__curMode_LB=0;$get("msgltMessage").innerHTML = __countDownTimeoutMsg;$common.setVisible($get("msg__divAlert"), true);$common.setVisible($get("msg__divConfirm"), false);$common.setVisible($get("msg__divContinue"), false);}}function ShowMessageLBCountDown(title, msg, tmsg, bid, mode, callback, timer) {if($find("msg_mpeMessage")) {__countDownTimer=timer;__countDownMsg=msg;__countDownTimeoutMsg=tmsg;var tmp = __countDownMsg.replace('[TIMER]', __countDownTimer);ShowMessageLB(title, tmp, bid, mode, callback);UpdateCountDown();}else{setTimeout(function(){ShowMessageLBCountDown(title, msg, bid, mode, callback, timer)}, 20);}}function ShowMessageLBYesNo(title, msg, bid, mode, callbackYes, callbackJsNo) {__callback_LB_NoButton = callbackJsNo;ShowMessageLB(title,msg,bid,mode,callbackYes);}function ShowMessageLB(title, msg, bid, mode, callback) {__callback_LB = callback;__curMode_LB = mode;__callBackBtnId = bid;$get("msglblHeader").innerHTML = title;$get("msgltMessage").innerHTML = msg;if( mode && mode == 1){$common.setVisible($get("msg__divConfirm"), true);$common.setVisible($get("msg__divAlert"), false);$common.setVisible($get("msg__divContinue"), false);}else if( mode && mode == 2){$common.setVisible($get("msg__divConfirm"), false);$common.setVisible($get("msg__divAlert"), false);$common.setVisible($get("msg__divContinue"), true);} else {$common.setVisible($get("msg__divAlert"), true);$common.setVisible($get("msg__divConfirm"), false);$common.setVisible($get("msg__divContinue"), false);}$find("msg_mpeMessage").show();}function __ShowLB_FrameDelay(clsName, hText, width, height, url) {if( $find("lbi_mpeIframe") ){__ShowLB_Frame(clsName, hText, width, height, url);}else{setTimeout(new function(){__ShowLB_FrameDelay(clsName, hText, width, height, url)}, 100);}}function __SetTitle_Frame(hText){$get("__ifHeaderImgTitle").innerHTML = hText;}function __SetClass_Frame(clsName){$get("__ifHeaderImgTitle").className = clsName;}function __ShowLB_Frame(clsName, hText, width, height, url){var myi = $get("__ifHeaderImgTitle");var myf = $get("__iFModal");var div = $get("lbi_pnl");myi.className = clsName;myi.innerHTML = hText;div.style.width = width;myf.style.width = width;myf.style.height = height;myf.src = url;$find("lbi_mpeIframe").show();}function __HideLB_Frame(callbackJs){$find("lbi_mpeIframe").hide();$get("__iFModal").src = "";if( callbackJs && callbackJs != '' ){eval(callbackJs);}}__InitUpdateProgressPanel();$('#lblSelectedcbCategory').html('All Categories');$('#lblSelectedrbBrand').html('All Brands');$('#lblSelectedcbChannel').html('All Channels');$('#lblSelectedddlKeyAccount').html('All APG');$('#lblSelectedddlCustomer').html('All Accounts');$('#lblSelectedcbRegion').html('All Regions');$('#lblSelectedddlProvince').html('All Provinces');$('#lblSelectedddlCity').html('All Cities');
WebForm_InitCallback();function __polling(message){var context = 'polling';WebForm_DoCallback('__Page',message,__pollingCallBack,context,__pollingErrorCallBack,true);}function __pollingCallBack(returnmessage, context){if(returnmessage.length > 0){eval(returnmessage)}else{__pollingStart(300000);}}function __pollingErrorCallBack(returnmessage, context){alert("Callback Error: " + returnmessage + ", " + context);}function __pollingStart(timeInterval){setTimeout("__polling('SS')", timeInterval);}__pollingStart(300000);(function() {var fn = function() {$get('tm_HiddenField').value = '';Sys.Application.remove_init(fn);};Sys.Application.add_init(fn);})();$('#divCon').width(8120);var periodClickCounter = 32;function __ShowLBLoading() { var lf = $find("lb_mpeLoading"); if( lf ) lf.show();} function __HideLBLoading() { var lf = $find("lb_mpeLoading"); if( lf ) lf.hide(); else setTimeout(function(){__HideLBLoading();}, 100);}Sys.Net.WebRequestManager.add_invokingRequest(__onInvoke); Sys.Net.WebRequestManager.add_completedRequest(__onComplete); var __loadingDigShowed = false; var __loadingST;var __showAjaxLoading=false;function __onInvoke(sender, args) { if( !__showAjaxLoading) return; __loadingST = setTimeout("__ShowLoadingDig()", 100);} function __ShowLoadingDig() { if( !__loadingDigShowed ) top.__ShowLBLoading(); __loadingDigShowed = true; } function __onComplete(sender, args) { __showAjaxLoading=false;if( __loadingDigShowed ) top.__HideLBLoading(); __loadingDigShowed = false; clearTimeout(__loadingST); } function pageUnload() { Sys.Net.WebRequestManager.remove_invokingRequest(__onInvoke); Sys.Net.WebRequestManager.remove_completedRequest(__onComplete); }Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Dashboard/Dashboard.aspx","id":"cbCategory_hme2","popupElement":$get("cbCategory_PopupMenu")}, null, null, $get("cbCategory_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Dashboard/Dashboard.aspx","id":"rbBrand_hme2","popupElement":$get("rbBrand_PopupMenu")}, null, null, $get("rbBrand_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Dashboard/Dashboard.aspx","id":"cbChannel_hme2","popupElement":$get("cbChannel_PopupMenu")}, null, null, $get("cbChannel_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Dashboard/Dashboard.aspx","id":"ddlKeyAccount_hme2","popupElement":$get("ddlKeyAccount_PopupMenu")}, null, null, $get("ddlKeyAccount_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Dashboard/Dashboard.aspx","id":"ddlCustomer_hme2","popupElement":$get("ddlCustomer_PopupMenu")}, null, null, $get("ddlCustomer_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Dashboard/Dashboard.aspx","id":"cbRegion_hme2","popupElement":$get("cbRegion_PopupMenu")}, null, null, $get("cbRegion_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Dashboard/Dashboard.aspx","id":"ddlProvince_hme2","popupElement":$get("ddlProvince_PopupMenu")}, null, null, $get("ddlProvince_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Dashboard/Dashboard.aspx","id":"ddlCity_hme2","popupElement":$get("ddlCity_PopupMenu")}, null, null, $get("ddlCity_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.ModalPopupBehavior, {"BackgroundCssClass":"modalBackground","PopupControlID":"msg_pnl","dynamicServicePath":"/StoreRadar/Dashboard/Dashboard.aspx","id":"msg_mpeMessage"}, null, null, $get("msg_btn"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.ModalPopupBehavior, {"BackgroundCssClass":"modalBackground","PopupControlID":"lbi_pnl","dynamicServicePath":"/StoreRadar/Dashboard/Dashboard.aspx","id":"lbi_mpeIframe"}, null, null, $get("lbi_btn"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.ModalPopupBehavior, {"BackgroundCssClass":"modalBackground","PopupControlID":"lb_pnl","dynamicServicePath":"/StoreRadar/Dashboard/Dashboard.aspx","id":"lb_mpeLoading"}, null, null, $get("lb_btn"));
});
//]]>
</script>
<div id="msg_mpeMessage_backgroundElement" class="modalBackground" style="display: none; position: fixed; left: 0px; top: 0px;"></div><div id="lbi_mpeIframe_backgroundElement" class="modalBackground" style="display: none; position: fixed; left: 0px; top: 0px;"></div><div id="lb_mpeLoading_backgroundElement" class="modalBackground" style="display: none; position: fixed; left: 0px; top: 0px;"></div>
</body>
</form>
</html>