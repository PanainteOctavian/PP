# Example file showing a circle moving on screen
import pygame

# pygame setup
pygame.init()

# screen size
width = 600
height = 600
screen = pygame.display.set_mode((width, height))

# Grid parameters
rows, cols = 3, 3  # Number of rows and columns
cell_size = width//cols  # Size of each cell

# Function to draw the grid
def draw_grid():
    for row in range(rows):
        for col in range(cols):
            # Calculate the position of each cell
            x = col * cell_size
            y = row * cell_size

            # Draw the cell as a rectangle
            pygame.draw.rect(screen, "black", (x, y, cell_size, cell_size), 1)

running = True
while running:
    # poll for events
    # pygame.QUIT event means the user clicked X to close your window
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    # fill the screen with a color to wipe away anything from last frame
    screen.fill("white")

    # draw the grid
    draw_grid()

    # flip() the display to put your work on screen
    pygame.display.flip()

pygame.quit()
