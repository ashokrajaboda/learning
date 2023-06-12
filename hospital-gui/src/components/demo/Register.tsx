import React, { useState } from "react";
import { ArrowRight } from "react-bootstrap-icons";
import { Resolver, useForm } from "react-hook-form";
import { RxReset } from "react-icons/rx";
import { Link } from "react-router-dom";

type UserRegisterForm = {
  fullname: string;
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  acceptTerms: boolean;
};

const Register: React.FC = () => {
  const [successful, setSuccessful] = useState<boolean>(false);
  const [message, setMessage] = useState<string>("");
  let initialValues: UserRegisterForm = {
    fullname: 'Ashok',
    username: 'ashokraja',
    email: 'ashok@test.com',
    password: '',
    confirmPassword: '',
    acceptTerms: false,
  }
  /*
    const resolver: Resolver<UserRegisterForm> = async (values) => {
      return {
          values: values? values: {},
          errors: {
            fullname: {
                  type: 'required',
                  message: 'fullname is required'
              },
              username: {
                type: 'required',
                message: 'username is required'
              }
          }
      };
    };
    */
  /*
    const validationSchema = Yup.object().shape({
      username: Yup.string()
        .test(
          "len",
          "The username must be between 3 and 20 characters.",
          (val: any) =>
            val &&
            val.toString().length >= 3 &&
            val.toString().length <= 20
        )
        .required("This field is required!"),
      email: Yup.string()
        .email("This is not a valid email.")
        .required("This field is required!"),
      password: Yup.string()
        .test(
          "len",
          "The password must be between 6 and 40 characters.",
          (val: any) =>
            val &&
            val.toString().length >= 6 &&
            val.toString().length <= 40
        )
        .required("This field is required!"),
    });
    */
  const { register, watch, handleSubmit, reset, setFocus, formState: { errors } } = useForm<UserRegisterForm>();
  const onSubmit = (data: UserRegisterForm) => {
    console.log(JSON.stringify(data, null, 2));
    alert('Saved Successfully : ' + JSON.stringify(data, null, 2));
  };
  React.useEffect(() => {
    setFocus("fullname");
  }, [setFocus]);

  //const onSubmit = handleSubmit((data) => console.log(data));
  /*
   const handleRegister = (formValue: IUser) => {
     const { username, email, password } = formValue;
 
     register(username, email, password).then(
       (response: any) => {
         setMessage(response.data.message);
         setSuccessful(true);
       },
       (error: any) => {
         const resMessage =
           (error.response &&
             error.response.data &&
             error.response.data.message) ||
           error.message ||
           error.toString();
 
         setMessage(resMessage);
         setSuccessful(false);
       }
     );
   };
   */

  return (
    <section className="section min-vh-100 register-form" >
      <div className="pagetitle">
        <h1>Register</h1>
        <nav>
          <ol className="breadcrumb">
            <li className="breadcrumb-item">
              <Link to={"/"} >
                Home
              </Link>
            </li>
            <li className="breadcrumb-item active">Register</li>
          </ol>
        </nav>
      </div>
      <section className="section register">
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="form-group">
            <label>Full Name</label>
            <input
              type="text"
              {...register('fullname', {
                value: initialValues.fullname,
                required: 'Enter Full name',
                maxLength: {
                  value: 30,
                  message: 'Full name Max length exceeded'
                },
                pattern: {
                  value: /^[a-zA-Z ]*$/,
                  message: 'Please enter Alphabets'
                }
              }
              )}
              className={`form-control ${errors.fullname ? 'is-invalid' : ''}`}
            />
            <div className="invalid-feedback">{errors.fullname?.message}</div>
          </div>

          <div className="form-group">
            <label>Username</label>
            <input
              type="text"
              {...register('username', {
                value: initialValues.username,
                required: 'Enter Username',
                maxLength: {
                  value: 30,
                  message: 'Username Max length exceeded'
                },
                pattern: {
                  value: /^[ A-Za-z0-9_@.]*$/,
                  message: 'Please enter Alpha Numeric and Special Characters(_@.)'
                }
              }
              )}
              className={`form-control ${errors.username ? 'is-invalid' : ''}`}
            />
            <div className="invalid-feedback">{errors.username?.message}</div>
          </div>

          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              {...register('email', {
                value: initialValues.email,
                required: 'Enter Email address',
                maxLength: {
                  value: 30,
                  message: 'Username Max length exceeded'
                },
                pattern: {
                  value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i,
                  message: 'Enter a valid e-mail address',
                }
              })}
              className={`form-control ${errors.email ? 'is-invalid' : ''}`}
            />
            <div className="invalid-feedback">{errors.email?.message}</div>
          </div>

          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              {...register('password', {
                value: initialValues.password,
                required: 'Enter Password',
                maxLength: {
                  value: 30,
                  message: 'Username Max length exceeded'
                },
                pattern: {
                  value: /^[ A-Za-z0-9_@.]*$/,
                  message: 'Please enter Alpha Numeric and Special Characters(_@.)',
                }
              })}
              className={`form-control ${errors.password ? 'is-invalid' : ''}`}
            />
            <div className="invalid-feedback">{errors.password?.message}</div>
          </div>
          <div className="form-group">
            <label>Confirm Password</label>
            <input
              type="password"
              {...register('confirmPassword', {
                value: initialValues.confirmPassword,
                required: 'Enter ConfirmPassword',
                validate: (val: string) => {
                  if (watch('password') != val) {
                    return "Your password do no match";
                  }
                }
              })}
              className={`form-control ${errors.confirmPassword ? 'is-invalid' : ''
                }`}
            />
            <div className="invalid-feedback">
              {errors.confirmPassword?.message}
            </div>
          </div>

          <div className="form-group form-check">
            <input
              type="checkbox"
              {...register('acceptTerms', {
                value: initialValues.acceptTerms,
                required: true
              }
              )}
              className={`form-check-input ${errors.acceptTerms ? 'is-invalid' : ''
                }`}
            />
            <label htmlFor="acceptTerms" className="form-check-label">
              I have read and agree to the Terms
            </label>
            <div className="invalid-feedback">{errors.acceptTerms?.message}</div>
          </div>

          <div className="form-group">
            <button type="submit" className="btn btn-primary">
              Register
            </button>
            <button
              type="button"
              onClick={() => reset()}
              className="btn btn-warning float-right"
            >
              <RxReset />
              Reset
            </button>
          </div>
        </form>
      </section>
    </section>
  );
};

export default Register;