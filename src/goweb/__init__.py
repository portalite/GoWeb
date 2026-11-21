import toga
from toga.style import Pack
from toga.style.pack import COLUMN, ROW


class GoWeb(toga.App):
    def startup(self):
        self.address = toga.TextInput(
            value="https://www.google.com",
            style=Pack(flex=1)
        )

        self.web = toga.WebView(
            url="https://www.google.com",
            style=Pack(flex=1)
        )

        go = toga.Button(
            "Git",
            on_press=self.open_site,
            style=Pack(padding=5)
        )

        bar = toga.Box(
            children=[self.address, go],
            style=Pack(direction=ROW, padding=5)
        )

        layout = toga.Box(
            children=[bar, self.web],
            style=Pack(direction=COLUMN, flex=1)
        )

        self.main_window = toga.MainWindow(title="GoWeb")
        self.main_window.content = layout
        self.main_window.show()

    def open_site(self, widget):
        url = self.address.value.strip()

        if url and not url.startswith(("http://", "https://")):
            url = "https://" + url

        if url:
            self.web.url = url


def main():
    return GoWeb()