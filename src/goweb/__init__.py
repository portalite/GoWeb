
import toga
from toga.style import Pack
from toga.style.pack import COLUMN

class GoWeb(toga.App):
    def startup(self):
        self.main_window = toga.MainWindow(title="GoWeb")

        self.main_window.content = toga.Box(
            children=[
                toga.Label("GoWeb çalışıyor!")
            ],
            style=Pack(
                direction=COLUMN,
                padding=20,
                flex=1
            )
        )

        self.main_window.show()

def main():
    return GoWeb()
