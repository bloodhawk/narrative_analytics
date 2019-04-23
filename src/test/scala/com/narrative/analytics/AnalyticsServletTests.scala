package com.narrative.analytics

import org.scalatra.test.scalatest._
import org.scalatest.{ FunSuite, BeforeAndAfter }


class AnalyticsServletTests extends FunSuite with ScalatraSuite with DBConnectionPool with BeforeAndAfter {
  addServlet(classOf[AnalyticsServlet], "/*")

  before {
    configureDb()
  }

  after {
    closeDbConnection()
  }

  test("analytics") {
    post("/analytics-create-db", params = None) {
      status should equal (200)
    }

    get("/analytics?timestamp=1555987406935", params = None) {
      status should equal (200)
      body should equal("unique_users,0\nclicks,0\nimpressions,0")
    }

    post(uri = "/analytics?timestamp=1555987406935&user=Aaron&event=Impression", params = None) {
      status should equal(204)
      body should equal("")
    }

    get("/analytics?timestamp=1555987406935", params = None) {
      body should equal("unique_users,1\nclicks,0\nimpressions,1")
    }
  }
}