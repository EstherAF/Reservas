<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script type="text/javascript">
    $(function() {
        
        $('[type="popup_resource"]').dialog({
                autoOpen: false,
                modal: true,
                position: { my: "center", at: "center", of: window }
            });
            
        $('.link').click(function() {
            id = $(this).attr("id");
            name = $(this).attr("name");
            description = $(this).attr("description");
            $('[type="popup_resource"] span.name').text(name);
            $('[type="popup_resource"] span.description').text(description);

            $('[type="popup_resource"]').dialog( "open" );

        });
    });
</script>
