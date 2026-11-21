
from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.textinput import TextInput
from kivy.uix.button import Button
from kivy.uix.label import Label
from kivy.utils import platform
import webbrowser


class GoWeb(App):
    def build(self):
        self.layout = BoxLayout(
            orientation="vertical",
            padding=8,
            spacing=8
        )

        self.layout.add_widget(Label(
            text="GoWeb",
            font_size=28,
            size_hint_y=None,
            height=55
        ))

        bar = BoxLayout(
            size_hint_y=None,
            height=50,
            spacing=5
        )

        self.address = TextInput(
            hint_text="Adres veya site yaz...",
            multiline=False
        )

        button = Button(text="Git")
        button.bind(on_press=self.open_site)

        bar.add_widget(self.address)
        bar.add_widget(button)
        self.layout.add_widget(bar)

        self.status = Label(text="GoWeb hazır")
        self.layout.add_widget(self.status)

        return self.layout

    def open_site(self, instance):
        url = self.address.text.strip()

        if not url:
            return

        if not url.startswith(("http://", "https://")):
            url = "https://" + url

        webbrowser.open(url)
        self.status.text = "Açılıyor: " + url


GoWeb().run()
