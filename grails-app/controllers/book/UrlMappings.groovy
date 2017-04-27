package book

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/overTime")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
