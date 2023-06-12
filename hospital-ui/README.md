# Getting Started with Create React App and Redux

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app), using the [Redux](https://redux.js.org/) and [Redux Toolkit](https://redux-toolkit.js.org/) template.

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can't go back!**

If you aren't satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you're on your own.

You don't have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn't feel obligated to use this feature. However we understand that this tool wouldn't be useful if you couldn't customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).


<nav className="navbar navbar-light d-md-none">
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
          data-bs-target="#sidebar" aria-controls="sidebar" aria-expanded="false"
          aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
      </nav>
      <div className="row">
        <aside id="sidebar" className="d-md-block col-md-3 col-lg-2 bg-primary min-vh-100 collapse">
          <ul className="navbar-nav mt-4 px-3">
            <li className="nav-item py-2"><a className="text-white text-decoration-none" href="#">Home</a></li>
            <li className="nav-item py-2"><a className="text-white text-decoration-none" href="#">Contact</a></li>
            <li className="nav-item py-2"><a className="text-white text-decoration-none" href="#">About</a></li>
          </ul>
        </aside>

        <main className="col-12 col-md-9 col-lg-10">
          <section className="min-vh-100 p-5 d-flex flex-column">
            <div className="bg-light p-5 flex-grow-1 d-flex justify-content-center align-items-center rounded">
              <h1 className="display-5 text-center">Main content</h1>
            </div>
          </section>
        </main>
      </div>

      <footer className="navbar fixed-bottom bg-secondary text-white ">
        <div className="container">
          Footer
        </div>
      </footer>
