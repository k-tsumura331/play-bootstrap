/**
 * Copyright 2014 Adrian Hurtado (adrianhurt)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package controllers

import javax.inject.Inject
import play.api.i18n.{ MessagesApi, I18nSupport }
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

case class ReadonlyDemoData(text: String, checkbox: Boolean, radio: String, select: String)

class Application @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {

  val fooForm = Form(single("foo" -> text(maxLength = 10)))

  def index = Action { implicit request => Ok(views.html.index(fooForm)) }
  def vertical = Action { implicit request => Ok(views.html.vertical(fooForm)) }
  def horizontal = Action { implicit request => Ok(views.html.horizontal(fooForm)) }
  def inline = Action { implicit request => Ok(views.html.inline(fooForm)) }
  def mixed = Action { implicit request => Ok(views.html.mixed(fooForm)) }

  val fooReadonlyForm = Form(
    mapping(
      "text" -> nonEmptyText,
      "checkbox" -> boolean,
      "radio" -> text,
      "select" -> text
    )(ReadonlyDemoData.apply)(ReadonlyDemoData.unapply)
  )
  def readonly = Action { implicit request => Ok(views.html.readonly(fooReadonlyForm, None)) }
  def handleReadonly = Action { implicit request =>
    fooReadonlyForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.readonly(formWithErrors, None)),
      data => Ok(views.html.readonly(fooReadonlyForm, Some(data)))
    )
  }

  def multifield = Action { implicit request => Ok(views.html.multifield(fooForm)) }
  def extendIt = Action { implicit request => Ok(views.html.extendIt(fooForm)) }
  def docs = Action { implicit request => Ok(views.html.docs(fooForm)) }

}
