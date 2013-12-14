<%--
  User: Jie
  Date: 12/10/13
  Time: 11:27 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>

    <img src="userImage/null.jpg"/>

    <form action="" method="post" id="info-form" enctype="multipart/form-data" class="info-form">

        <div>
            <label for="m_logo">上传新logo</label>
            <input id="m_logo" name="m_logo" type="file"/>
        </div>

        <div>
            <label for="m_name">名称</label>
            <input id="m_name" name="m_name" type="text"/>
        </div>
        <div>
            <label for="m_addr">地址</label>
            <input id="m_addr" name="m_addr" type="text"/>
        </div>
        <div>
            <label for="m_tel">电话</label>
            <input id="m_tel" name="m_tel" type="text"/>
        </div>
        <div>
            <label for="m_major">主营</label>
            <input id="m_major" name="m_major" type="text"/>
        </div>
        <div>
            <label for="m_sign">签名</label>
            <input id="m_sign" name="m_sign" type="text"/>
        </div>

        <div>
            <label for="m_intro">简介</label>
            <textarea name="m_intro" id="m_intro"></textarea>
        </div>

        <div>
            <button type="submit">提交修改</button>
        </div>

    </form>

</div>