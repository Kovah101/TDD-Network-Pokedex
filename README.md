# Pokedex App

Welcome to the Pokedex App! This application showcases all the Pokémon from the Kanto and Johto regions in two separate tabs. It features a robust search functionality allowing users to search by both name and number. Initially built to practice Test-Driven Development (TDD), this project now includes enhanced UI with Compose animations and a comparison between GraphQL and Retrofit for networking.

## Features

- **View Pokémon**: Browse all Pokémon from the Kanto and Johto regions.
- **Search Functionality**: Search for Pokémon by name or number.
- **Modern UI**: Enjoy a beautiful and responsive UI with Compose animations.
- **GraphQL Integration**: Faster and more efficient data loading with GraphQL.
- **Test Coverage**: Extensive testing on Retrofit service, database, and repository layers.
- **Modular Architecture**: Built with modular components to ensure scalability.

## Future Plans

- **Compose Navigation Animations**: Integrate smooth navigation animations with Compose when they come out of alpha.

## Getting Started

### Prerequisites

- Android Studio
- A device or emulator running Android 5.0 (Lollipop) or higher

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/pokedex-app.git
2. Open the project in Android Studio.
3. Build and run the app on your device or emulator.

### Dependencies

- **Retrofit**: For REST API calls.
- **Apollo**: For GraphQL queries and mutations.
- **Room**: For local database storage.
- **Jetpack Compose**: For modern UI development.
- **Hilt**: For dependency injection.
- **Mockito**: For mocking in tests.

## Architecture

The app follows MVVM (Model-View-ViewModel) architecture, ensuring a clean separation of concerns and facilitating testability. It is also built with modular components to ensure scalability and maintainability.

### Modules

- **Data**: Contains the data sources (local and remote) and repository implementations.
- **UI**: Contains the ViewModel and Compose UI components.
- **Domain**: Contains the business logic.

## Testing

The project includes a comprehensive suite of tests:

- **Retrofit Service Tests**: Ensure the correctness of API calls.
- **Database Tests**: Validate data persistence and retrieval.
- **Repository Tests**: Check the data flow and business logic.
- **Apollo Service Tests**: Validate GraphQL queries and mutations.

### Running Tests

To run the tests, use the following command in Android Studio:
```bash
./gradlew test
```
## Reflections

During the development of this project, I found GraphQL to be superior to Retrofit for my purposes, offering faster data loading and more efficient queries. This experience has been invaluable in understanding the benefits and trade-offs of different networking approaches.

## Contributing

Contributions are welcome! If you have any ideas or improvements, please open an issue or submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/git/git-scm.com/blob/main/MIT-LICENSE.txt) file for details.

## Contact

For any questions or feedback, please contact me at et_boy101@hotmail.com

---

Thank you for checking out the Pokedex App! I hope you enjoy using it as much as I enjoyed building it.



