import {
  CardWrapper,
  CardHeader,
  CardHeading,
  CardBody,
  CardFieldset,
  CardInput,
  CardSelect,
  CardButton,
  Validation,
} from "./TokenGen.styles";
import { useEffect, useState } from "react";
import { getServiceTypesForBranch, requestNewToken } from "../../api/APIUtils";
import Joi from "joi-browser";

const TokenForm = () => {
  const [serviceType, setServiceType] = useState([]);
  const [selectServiceType, setSelectServiceType] = useState("");
  const [branches, setBranches] = useState(["001", "002", "003"]);
  const [selectedBranch, setSelectedBranch] = useState("");
  const [name, setName] = useState("");
  const [errors, setErrors] = useState({}, "");

  let initialSchema = {
    selectServiceType: Joi.string().required().label("Service Type"),
    selectedBranch: Joi.string().required().label("Branch"),
    name: Joi.string().min(3).required().label("Name"),
  };

  useEffect(() => {
    async function fetchServiceTypes() {
      const response = await getServiceTypesForBranch("001");
      setServiceType(response.data.data);
    }
    fetchServiceTypes();
  }, []);

  let validateProperty = (key, value) => {
    const obj = { [key]: value };
    const schema = Joi.object({ [key]: initialSchema[key] });

    const { error } = Joi.validate(obj, schema);
    return error ? error.details[0].message : null;
  };

  let handleOnChange = (e) => {
    let key = e.target.name;
    let value = e.target.value;
    const errorMassage = validateProperty(key, value);
    if (errorMassage) errors[key] = errorMassage;
    else delete errors[key];

    setErrors(errors);

    if (key === "selectedBranch") setSelectedBranch(value);
    else if (key === "selectServiceType") setSelectServiceType(value);
    else setName(value);
  };

  let getToken = async () => {
    console.log(errors);
    if (Object.keys(errors).length === 0) {
      let res = await requestNewToken("001", selectServiceType);
      setName("");
      setSelectedBranch("");
      setSelectServiceType("");
      autoPrintToken(res.data.data.tokenNumber);
    }
  };
  let autoPrintToken = (token) => {
    var mywindow = window.open("", "PRINT", "height=10,width=10");
    mywindow.document.write(
      '<div style="font-family: Calibri, Helvetica, Arial, sans-serif;">'
    );
    mywindow.document.write(
      '<h1 style="font-size :100px;">' + `${token}` + "</h1>"
    );
    mywindow.document.write("<hr/>");
    mywindow.document.write(
      '<p style="font-size: 19px;text-align: right;"><i>' +
        `${new Date()}` +
        "</i></p>"
    );
    mywindow.document.write("</div>");
    mywindow.print();
    mywindow.close();
    mywindow.history.back();
    return true;
  };

  let displayError = (key) => <Validation>{errors[key]}</Validation>;

  return (
    <div className="App">
      <CardWrapper>
        <CardHeader>
          <CardHeading>Token Generation Form</CardHeading>
        </CardHeader>
        <CardBody>
          <CardFieldset>
            <CardSelect
              required
              name="selectedBranch"
              value={selectedBranch}
              onChange={handleOnChange}
            >
              <option value="" hidden>
                --Select branch--
              </option>
              {branches.map((m) => (
                <option key={m} value={m}>
                  {m}
                </option>
              ))}
            </CardSelect>
            {errors.selectedBranch && displayError("selectedBranch")}
          </CardFieldset>

          <CardFieldset>
            <CardSelect
              required
              name="selectServiceType"
              value={selectServiceType}
              onChange={handleOnChange}
            >
              <option value="" hidden>
                --Select service type--
              </option>
              {serviceType.map((m) => (
                <option key={m.serviceTypeId} value={m.serviceTypeId}>
                  {m.serviceTypeDescription}
                </option>
              ))}
            </CardSelect>
            {errors.selectServiceType && displayError("selectServiceType")}
          </CardFieldset>

          <CardFieldset>
            <CardInput
              placeholder="Name"
              value={name}
              name="name"
              onChange={handleOnChange}
              type="text"
              required
            />
            {errors.name && displayError("name")}
          </CardFieldset>

          <CardFieldset>
            <CardButton type="button" onClick={getToken}>
              Get Token
            </CardButton>
          </CardFieldset>
        </CardBody>
      </CardWrapper>
    </div>
  );
};

export default TokenForm;
