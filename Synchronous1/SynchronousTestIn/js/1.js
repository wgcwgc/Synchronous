package wgcwgc.lanchang;

public class test
{
//	��ҳ����js����??
//	????1��jsʵ��div����Ӧ�߶�??
//	�������£�?
	<script?type="text/javascript">?<!--?
	              window.onload=window.onresize=function()
	{
	    ?
	    if(document.getElementById("mm2").clientHeight<document.getElementById("mm1").clientHeight)
	    {
	        ?
	        document.getElementById("mm2").style.height=document.getElementById("mm1").offsetHeight+"px";
	        ?
	    }?else
	    {
	        ?
	        document.getElementById("mm1").style.height=document.getElementById("mm2").offsetHeight+"px";
	        ?
	    }?
	}?-->?</script>?
//	1������?ǰ��?
	<input?onclick=��history.go(-1)��?type=��button��?value=�����ˡ�?/>?
	                <input?onclick=��history.go(?1?);
	return?true;
	��?type=��button��?value=��ǰ����?/>?
//	                              2������?
//	                              <form><input?onclick=��history.back(-1)��?type=��button��?value=��������һ����?/></form>3��/�鿴Դ��?
	                                      <input?onclick=��window.location=��?name=��view��?type=��button��?value=���鿴Դ�롱?/>?

var script = document.createElement('script');
	script.src = 'http://static.pay.baidu.com/resource/baichuan/ns.js';
	document.body.appendChild(script);


//	4����ֹ�鿴Դ��?5��ˢ�°�ťһ?
	<input?onclick=��ReloadButton()��?type=��button��?value=��ˢ�°�ťһ��?/>?<script?type=��text/javascript��>//?<![CDATA[?function?ReloadButton(){location.href="i001.htm";}?//?]]></script>?
	                ˢ�°�ť��?
	                <input?onclick=��history.go(0)��?type=��button��?value=��ˢ�°�ť����?/>?
//	                                6������ҳ��ť?
	                                <input?onclick=��HomeButton()��?type=��button��?value=����ҳ��?/>?<script?type=��text/javascript��>//?<![CDATA[?
	                                        function?HomeButton()
	{
	location.href=http://www.winliuxq.com;}?//?]]></script>?
//	                  7�����������?
	                  <input?onclick=��AlertButton()��?type=��button��?value=�����������?/>?<script?type=��text/javascript��>//?<![CDATA[?
	                                  function?AlertButton()
	    {
	        window.alert("Ҫ������ѽ��");
	    }?//?]]></script>?
//	    8��״̬����Ϣ?
	    <input?onclick=��StatusButton()��?type=��button��?value=��״̬����Ϣ��?/>?<script?type=��text/javascript��>//?<![CDATA[?
	                    function?StatusButton()
	    {
	        window.status="Ҫ������ѽ��";
	    }?//?]]></script>?
//	    9������ɫ�任?




	    var script = document.createElement('script');
	    script.src = 'http://static.pay.baidu.com/resource/baichuan/ns.js';
	    document.body.appendChild(script);







	    ?
	    ?
	    <form><input?onclick=��BgButton()��?type=��button��?value=������ɫ�任��?/></form><script?type=��text/javascript��>//?<![CDATA[?function?BgButton(){?
	                          if?(document.bgColor=='#00ffff')? {document.bgColor='#ffffff';}?else
	        {
	            document.bgColor='#00ffff';
	        }?
	}?
	//?]]></script>?
//	10�����´���?
	<input?onclick=��NewWindow()��?type=��button��?value=�����´��ڡ�?/>?<script?type=��text/javascript��>//?<![CDATA[?function?
	                                        NewWindow()
	{
	    window.open("c01.htm","","height=240,width=340,status=no,location=no,toolbar=no,directories=no,menubar=no");
	}?//?]]></script>?
//	11��������С��?
	<object?id=��min��?classid=��clsid:
	                               d27cdb6e-ae6d-11cf-96b8-444553540000��?width=��100��?height=��100��?
	                                       codebase=��http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0��><param?name=��Command��?value=��Minimize��?/><embed?id=��min��?type=��application/x-shockwave-flash��?width=��100��?height=��100��?
	                                               command=��Minimize��></embed></object><button?onclick=��min.Click()��>������С��</button>?
//	                                                       12��ȫ������?
	                                                       <input?onclick=��window.open(document.location,??butong_net?,??fullscreen?)��?name=��FullScreen��?type=��BUTTON��?value=��ȫ����ʾ��?/>

}
