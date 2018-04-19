
/*
 *  Created on 5 de Marzo de 2008, 18:24
 *  @author Wilmer Manzaneda Chavez
 *  @group ISAWI
*/
var xml;
var xml2;
nodeIcon=new Image();
nodeIcon2=new Image();
nodeIcon3=new Image();
function parserXMLUsuariosZeus(codigo){
    //alert("entrororororor");
    var main= document.getElementById('main');
    var ajax=creaAjax();
    var url='../ServletUsuariosZeus?codigo='+codigo;
    url+='&pq='+(Math.random()*1000);

    ajax.open ('GET', url, true);
    ajax.onreadystatechange = function() {
        if (ajax.readyState==1) {
            var p=document.createElement('img');
            p.src='../img/load.gif';
            var div=document.createElement('div');
            div.style.paddingTop='150px';
            div.style.paddingLeft='20px';
            div.style.textAlign='center';
            div.style.top='0px';
            div.style.left='0px';
            div.style.position='absolute';
            div.innerHTML='CARGANDO...';
            div.style.fontFamily='Verdana';
            div.style.fontSize='11px';
            div.style.width='200px';
            div.appendChild(p);
            div.style.filter='alpha(opacity=40)';
            main.appendChild(div);
        }else if(ajax.readyState==4){
            if(ajax.status==200){
                xml=ajax.responseXML;
                clearChild(main);
                xmlResponse();

            }
        }
    }
    ajax.send(null);
}
function creaAjax(){
    var objetoAjax=false;
    try {
        /*Para navegadores distintos a internet explorer*/
        objetoAjax = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (e) {
        try {
            /*Para explorer*/
            objetoAjax = new ActiveXObject("Microsoft.XMLHTTP");
        }
        catch (E) {
            objetoAjax = false;
        }
    }

    if (!objetoAjax && typeof XMLHttpRequest!='undefined') {
        objetoAjax = new XMLHttpRequest();
    }
    return objetoAjax;
}

function xmlResponse(){
    // readXML();
    //alert(xml.getElementsByTagName('iconElement').length);
    nodeIcon.src= xml.getElementsByTagName('iconElement')[0].attributes[0].nodeValue;
    nodeIcon2.src=xml.getElementsByTagName('iconElement')[0].attributes[1].nodeValue;
    nodeIcon3.src=xml.getElementsByTagName('iconElement')[0].attributes[2].nodeValue;
    var treeNodes=  xml.getElementsByTagName('treeNode');
    var size=treeNodes.length;

    var ul=document.createElement('ul');
    ul.id='tree';

    for(var i=0;i<size;i++){
        var nodeName=treeNodes[i].attributes[4];
        if(nodeName.nodeValue=='root'){
            var li=document.createElement('li');
            var a=document.createElement('a');
            a.className='node';
            a.target='mainFrame';

            var node=document.createElement('img');
            var icon=document.createElement('img');
            node.src=nodeIcon.src;
            li.appendChild(node);
            icon.src=treeNodes[i].attributes[5].nodeValue;
            li.appendChild(icon);
            a.innerHTML=treeNodes[i].attributes[1].nodeValue;
            a.title=treeNodes[i].attributes[1].nodeValue;
            a.href='#';
            li.appendChild(a);
            li.id=treeNodes[i].attributes[3].nodeValue;
            node.onclick=function(){
                onClickEvent(this);
            }
            ul.appendChild(li);
        }
    }
    main.appendChild(ul);
}

function readXML(){
    var main= document.getElementById('main');
    xml=new ActiveXObject("Microsoft.XMLDOM");
    xml.async='false';
    var url='../arbol?codigo=60';
    xml.load(url);
//xml.load('../config/XMLReponseTree.xml');

}
function parserXML(){

    
    var main= document.getElementById('main');
    var ajax=creaAjax();
    var url='../servletUsuarios?codigo=60';
    url+='&pq='+(Math.random()*1000);
    ajax.open ('GET', url, true);
    ajax.onreadystatechange = function() {

        if (ajax.readyState==1) {
            var p=document.createElement('img');
            p.src='../img/load2.gif';
            var div=document.createElement('div');
            div.style.paddingTop='150px';
            div.style.paddingLeft='20px';
            div.style.textAlign='center';
            div.style.top='0px';
            div.style.left='0px';
            div.style.position='absolute';
            div.innerHTML='CARGANDO...';
            div.style.fontFamily='Verdana';
            div.style.fontSize='11px';
            div.style.width='200px';
            div.appendChild(p);
            //div.style.filter='alpha(opacity=40)';
            main.appendChild(div);
        }else if(ajax.readyState==4){

            if(ajax.status==200){
                xml=ajax.responseXML;
                clearChild(main);
                if(ajax.responseText==''){
                    var p1=document.createElement('img');
                    p1.src='../img/connect_disconnected.gif';
                    var div=document.createElement('div');
                    div.style.paddingTop='150px';
                    div.style.paddingLeft='20px';
                    div.style.textAlign='center';
                    div.style.top='0px';
                    div.style.left='0px';
                    div.style.position='absolute';
                    div.innerHTML='ERROR';
                    div.style.fontFamily='Verdana';
                    div.style.fontSize='11px';
                    div.style.width='200px';
                    div.appendChild(p1);
                     main.appendChild(div);

                }else{

                    xmlResponse();
                }



            }else{
                clearChild(main);
                var p=document.createElement('img');
                p.src='../img/connect_disconnected2.gif';
                var div=document.createElement('div');
                div.style.paddingTop='150px';
                div.style.paddingLeft='20px';
                div.style.textAlign='center';
                div.style.top='0px';
                div.style.left='0px';
                div.style.position='absolute';
                //div.innerHTML='CARGANDO...';
                div.style.fontFamily='Verdana';
                div.style.fontSize='11px';
                div.style.width='200px';
                div.appendChild(p);


            }
        }
    }
    ajax.send(null);
}
function parserXMLUsuarios(codigo){
    var main= document.getElementById('main');
    var ajax=creaAjax();
    var url='../servletUsuarios?codigo='+codigo;
    url+='&pq='+(Math.random()*1000);

    ajax.open ('GET', url, true);
    ajax.onreadystatechange = function() {
        if (ajax.readyState==1) {
            var p=document.createElement('img');
            p.src='../img/load.gif';
            var div=document.createElement('div');
            div.style.paddingTop='150px';
            div.style.paddingLeft='20px';
            div.style.textAlign='center';
            div.style.top='0px';
            div.style.left='0px';
            div.style.position='absolute';
            div.innerHTML='CARGANDO...';
            div.style.fontFamily='Verdana';
            div.style.fontSize='11px';
            div.style.width='200px';
            div.appendChild(p);
            //div.style.filter='alpha(opacity=40)';
            main.appendChild(div);
        }else if(ajax.readyState==4){
            if(ajax.status==200){
                xml=ajax.responseXML;
                clearChild(main);
                xmlResponse();

            }
        }
    }
    ajax.send(null);
}
function clearChild(obj){
    if(obj.hasChildNodes())
        obj.removeChild(obj.lastChild);

}

function onClickEvent(element){

    obj=element.parentNode;
    if(element.src==nodeIcon2.src){
        element.src=nodeIcon.src;
        clearChild(obj);
    }else{
        element.src=nodeIcon2.src;
        var treeNode=xml.getElementsByTagName('treeNode');
        var ul=document.createElement('ul');
        for(var j=0;j<treeNode.length;j++){
            if(treeNode[j].attributes[4]!=null){
                if(treeNode[j].attributes[4].nodeValue==obj.id){
                    var li=document.createElement('li');
                    li.className='n';

                    var node=document.createElement('img');
                    var folder=document.createElement('img');
                    var a=document.createElement('a');
                    a.className='node';
                    a.target='mainFrame';
                    if(treeNode[j].attributes[0].nodeValue=='false')
                        node.src=nodeIcon3.src;
                    else
                        node.src=nodeIcon.src;
                    li.appendChild(node);
                    folder.src=treeNode[j].attributes[5].nodeValue;
                    li.appendChild(folder);
                    li.appendChild(a);
                    a.innerHTML=treeNode[j].attributes[1].nodeValue;
                    a.href=treeNode[j].attributes[2].nodeValue;
                    a.title=treeNode[j].attributes[1].nodeValue;
                    li.id=treeNode[j].attributes[3].nodeValue;
                    ul.appendChild(li);

                    if(treeNode[j].attributes[0].nodeValue=='true'){
                        node.onclick=function (){
                            onClickEvent(this);
                        }
                    }
                }
            }
        }
        obj.appendChild(ul);
    }
}